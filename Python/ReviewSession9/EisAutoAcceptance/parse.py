import codecs
import datetime as dt
import os
import re
from configparser import ConfigParser
from xml.etree import ElementTree as et

from bs4 import BeautifulSoup as bs
from progress.bar import IncrementalBar
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as ec
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.wait import WebDriverWait

from src.auth import Day
from src.models import Fine, RegisterPayment, SinglePayment


class RegisterParser:
    def __init__(self):
        self.cfg = ConfigParser()
        self.cfg.read_file(codecs.open("config.ini", "r", "utf-8"))

    def open_xml_files(self):
        months = os.listdir(self.cfg["dir"]["year"])
        self.regs = f"{self.cfg['dir']['year']}\\{months[len(months)-1]}\\{self.cfg['dir']['day']}"
        for reg in [reg for reg in os.listdir(self.regs) if reg[-4:] == ".XML"]:
            with open(f"{self.regs}\\{reg}", "r", encoding="utf-8") as xml:
                self.parse_pay_data(
                    et.parse(xml)
                    .getroot()
                    .find("DataArea")
                    .find("Header")
                    .find("Report")
                )

    def parse_pay_data(self, register):
        identify = False
        if register.find("KBK").text == self.cfg["code"]["fine"].split(";")[0]:
            payments = []
            for pay in register.findall("RegPP"):
                fine, number, uin = [None, None, None]
                purpose = pay.find("PURPOSE").text
                if re.search(self.cfg["pattern"]["uin"], pay.find("PAYMENT_ID").text):
                    uin = re.search(
                        self.cfg["pattern"]["uin"], pay.find("PAYMENT_ID").text
                    ).group(0)
                elif re.search(self.cfg["pattern"]["uin"], purpose):
                    uin = re.search(self.cfg["pattern"]["uin"], purpose).group(0)
                if re.search(self.cfg["pattern"]["fine"], purpose):
                    number = (
                        re.search(self.cfg["pattern"]["fine"], purpose).group(0).upper()
                    )
                if uin and number:
                    fine = Fine.get_or_create(uin=uin, number=number)[0]
                    identify = True
                elif uin:
                    fine = Fine.get_or_create(uin=uin)[0]
                    identify = True
                elif number:
                    fine = Fine.get_or_create(number=number)[0]
                    identify = True
                payment = {
                    "num": int(register.find("NOM_PP").text),
                    "date": dt.datetime.strptime(
                        self.cfg["dir"]["day"], "%d.%m.%Y"
                    ).date(),
                    "payorder_id": int(pay.find("NOM_LINE").text),
                    "amount": float(pay.find("SUM_REESTR_PP").text) / 100,
                    "payer": pay.find("FIO_PLAT").text.upper(),
                    "payer_code": pay.find("INN_PLAT").text,
                    "fine": fine,
                }
                payments.append(payment)
        if identify:
            for payment in payments:
                RegisterPayment.get_or_create(**payment)


class PaymentsParser(Day):
    def __init__(self):
        super().__init__()
        self.open_day()
        self.titles = self.cfg["title"]["bank day"].split(";")
        self.payments_tab = bs(self.driver.page_source, "lxml").find(
            "table", wicketpath="table"
        )
        self.titles_id = self.get_titles_id()
        self.payments = [
            tr
            for tr in self.payments_tab.tbody.findChildren("tr")
            if tr.get("wicketpath")
        ]
        self.progress_bar = IncrementalBar(
            self.cfg["title"]["type"], max=len(self.payments)
        )

    def determine_type(self, tag, code):
        if tag.find(selected="selected"):
            pay_type = tag.find(selected="selected").text
        elif code == self.cfg["code"]["duty"]:
            pay_type = self.cfg["code_title"]["duty"]
        elif code in self.cfg["code"]["spec"].split(";"):
            pay_type = self.cfg["code_title"]["spec"]
        elif code in self.cfg["code"]["fine"].split(";"):
            pay_type = self.cfg["code_title"]["fine"]
        else:
            pay_type = ""
        return pay_type

    def parse_payment(self, elem_id, tag):
        tag_childs = tag.findChildren("td")
        payment = {
            "date": dt.datetime.strptime(self.cfg["dir"]["day"], "%d.%m.%Y").date(),
            "elem_id": elem_id,
        }
        fine = None
        for i, td_tag in enumerate(tag_childs):
            if i == 0:
                pay_type = self.determine_type(
                    td_tag, tag_childs[len(tag_childs) - 1].text.strip()
                )
            elif i == self.titles_id[self.titles[0]]:
                payment["num"] = int(td_tag.text.strip())
            elif i == self.titles_id[self.titles[1]]:
                uin = re.search(self.cfg["pattern"]["uin"], td_tag.text)
                number = re.search(self.cfg["pattern"]["fine"], td_tag.text)
                fine = None
                if uin and number:
                    fine = Fine.get_or_create(
                        uin=uin.group(0), number=number.group(0).upper()
                    )[0]
                elif uin:
                    fine = Fine.get_or_create(uin=uin.group(0))[0]
                elif number:
                    fine = Fine.get_or_create(number=number.group(0).upper())[0]
                if fine:
                    pay_type = self.cfg["code_title"]["fine"]
            elif i == self.titles_id[self.titles[2]]:
                payment["is_taken"] = False
                if len(td_tag.text.strip()) > 0:
                    payment["is_taken"] = True
            payment["fine"] = fine
        if pay_type == self.cfg["code_title"]["fine"] and payment["is_taken"] is False:
            SinglePayment.get_or_create(**payment)
        return payment, pay_type

    def set_types(self, pay_type=None):
        for i, tag in enumerate(self.payments, start=1):
            payment_data, type_text = self.parse_payment(i, tag)
            if not tag.td.find(selected="selected"):
                pay_type = Select(
                    self.driver.find_element_by_name(
                        f"table:body:rows:{payment_data['elem_id']}"
                        ":cells:1:cell:columnEditorDiv:comp"
                    )
                )
                pay_type.select_by_visible_text(type_text)
                if type_text == self.cfg["code_title"]["fine"]:
                    elem_id = payment_data["elem_id"]
                    WebDriverWait(self.driver, 120).until(
                        ec.presence_of_element_located(
                            (
                                By.XPATH,
                                '//*[@wicketpath="table_body_rows_'
                                f'{elem_id}_cells_2"]/div/div/a/img',
                            )
                        )
                    )
            self.progress_bar.next()
        self.progress_bar.finish()
        self.driver.quit()

    def get_titles_id(self):
        titles_id = {}
        for i, tag in enumerate(self.payments_tab.thead.tr.findChildren("th")):
            if tag.text in self.titles:
                titles_id[tag.text] = i
        return titles_id
