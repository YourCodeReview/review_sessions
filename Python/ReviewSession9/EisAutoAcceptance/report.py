import codecs
import datetime as dt
import os
from configparser import ConfigParser

import xlsxwriter

from src.models import RegisterPayment, SinglePayment


class Report:
    def __init__(self):
        self.cfg = ConfigParser()
        self.cfg.read_file(codecs.open("config.ini", "r", "utf-8"))
        self.date = dt.datetime.strptime(self.cfg["dir"]["day"], "%d.%m.%Y").date()
        self.report_titles = self.cfg["title"]["table"].split(";")
        self.title_size = [15, 15, 22, 46, 46]
        try:
            os.makedirs(self.cfg["title"]["report"])
        except FileExistsError:
            pass

    def check_payer(self, payment):
        is_match = False
        if payment.payer:
            payer = payment.payer.replace("Ё", "Е")
            debtor = payment.fine.debtor.replace("Ё", "Е")
            code = payment.payer_code
            special_payers = self.cfg["title"]["special payers"].split(";")
            if payer.find(special_payers[0]) != -1:
                is_match = True
            elif payer.find(special_payers[1]) != -1:
                is_match = True
            elif payer.find(debtor) != -1:
                is_match = True
            elif debtor.find(payer) != -1:
                is_match = True
            elif code == payment.fine.debtor_code and code != 0:
                is_match = True
        return is_match

    def save_report(self):
        with xlsxwriter.Workbook(
            f"{self.cfg['title']['report']}\\{self.cfg['dir']['day']}.xlsx"
        ) as workbook:
            titles = workbook.add_format(
                {"bold": True, "align": "center", "valign": "vcenter", "border": 1}
            )
            nums = workbook.add_format(
                {"align": "center", "valign": "top", "border": 1}
            )
            amount = workbook.add_format(
                {
                    "num_format": "# ### ##0.00",
                    "align": "right",
                    "valign": "top",
                    "border": 1,
                }
            )
            text = workbook.add_format(
                {"text_wrap": True, "valign": "top", "border": 1}
            )

            worksheet = workbook.add_worksheet(f"{self.cfg['dir']['day']}")

            for i, title in enumerate(self.report_titles):
                worksheet.write(0, i, title, titles)
                worksheet.set_column(i, i, self.title_size[i])

            single_payments = SinglePayment.select().where(
                SinglePayment.date == self.date,
                SinglePayment.fine.is_null(False),
            )

            register_payments = RegisterPayment.select().where(
                RegisterPayment.date == self.date
            )

            report_line = 1
            for payment in single_payments.objects():
                if (
                    payment.fine
                    and payment.fine.debtor
                    and not self.check_payer(payment)
                ):
                    worksheet.write(report_line, 0, payment.num, nums)
                    worksheet.write(report_line, 1, payment.amount, amount)
                    worksheet.write(report_line, 2, payment.fine.number, nums)
                    worksheet.write(report_line, 3, payment.payer, text)
                    worksheet.write(report_line, 4, payment.fine.debtor, text)
                    report_line += 1

            for payment in register_payments.objects():
                if (
                    payment.fine
                    and payment.fine.debtor
                    and not self.check_payer(payment)
                ):
                    worksheet.write(
                        report_line,
                        0,
                        str(payment.num) + "/" + str(payment.payorder_id),
                        nums,
                    )
                    worksheet.write(report_line, 1, payment.amount, amount)
                    worksheet.write(report_line, 2, payment.fine.number, nums)
                    worksheet.write(report_line, 3, payment.payer, text)
                    worksheet.write(report_line, 4, payment.fine.debtor, text)
                    report_line += 1
