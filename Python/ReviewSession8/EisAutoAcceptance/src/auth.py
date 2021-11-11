import codecs
from configparser import ConfigParser

from selenium import webdriver
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as ec
from selenium.webdriver.support.wait import WebDriverWait


class Driver:
    def __init__(self, hidden=True):
        options = Options()
        options.add_argument("--disable-extensions")
        options.add_argument("--disable-gpu")
        options.add_experimental_option("excludeSwitches", ["enable-logging"])
        # if hidden:
        #     options.add_argument("--headless")
        self.driver = webdriver.Chrome("chromedriver.exe", options=options)
        self.cfg = ConfigParser()
        self.cfg.read_file(codecs.open("config.ini", "r", "utf-8"))

    def quit(self):
        self.driver.quit()


class Login(Driver):
    def __init__(self):
        super().__init__(hidden=False)

    def log_in(self):
        self.driver.get(self.cfg["url"]["login"])
        self.driver.find_element_by_name("username").send_keys(self.cfg["user"]["name"])
        try:
            WebDriverWait(self.driver, 300).until(
                ec.presence_of_element_located((By.ID, "cssmenu"))
            )
            with open("config.ini", mode="w", encoding="utf-8") as config:
                self.cfg["cookie"]["value"] = self.driver.get_cookies()[0]["value"]
                self.cfg.write(config)
        except TimeoutException:
            pass
        finally:
            self.quit()


class Day(Driver):
    def __init__(self):
        super().__init__(hidden=True)
        self.cookie = {
            "domain": self.cfg["cookie"]["domain"],
            "httpOnly": self.cfg.getboolean("cookie", "httpOnly"),
            "name": self.cfg["cookie"]["name"],
            "path": self.cfg["cookie"]["path"],
            "secure": self.cfg.getboolean("cookie", "secure"),
            "value": self.cfg["cookie"]["value"],
        }

    def open_day(self):
        self.driver.get(self.cfg["url"]["matching"])
        self.driver.delete_all_cookies()
        self.driver.add_cookie(self.cookie)
        self.driver.refresh()
        self.driver.implicitly_wait(2)
        self.driver.find_element_by_name("bankDay").send_keys(self.cfg["dir"]["day"])
        WebDriverWait(self.driver, 20).until(
            ec.element_to_be_clickable((By.NAME, "search"))
        ).click()
        try:
            WebDriverWait(self.driver, 120).until(
                ec.presence_of_element_located(
                    (By.NAME, "table:body:rows:1:cells:1:cell:columnEditorDiv:comp")
                )
            )
        except TimeoutException:
            self.quit()
