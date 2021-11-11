import codecs
import multiprocessing as mp
import os
import sys
from configparser import ConfigParser

from prettytable import PrettyTable

from src import auth, matching, parse, report


class Menu:
    def __init__(self):
        self.manager = matching.Payments()
        self.cfg = ConfigParser()
        self.cfg.read_file(codecs.open("config.ini", "r", "utf-8"))
        self.menu = {
            "1": (self.cfg["title"]["day choosing"], self.choose_day),
            "2": (self.cfg["title"]["log in"], self.log_in),
            "3": (self.cfg["title"]["type"], self.set_types),
            "4": (self.cfg["title"]["single"], self.single_payments),
            "5": (self.cfg["title"]["register"], self.regitser_payments),
            "6": (self.cfg["title"]["make report"], self.save_report),
            "0": (self.cfg["title"]["exit"], sys.exit),
        }

    def show(self):
        # os.system("cls||clear")
        self.table = PrettyTable()
        self.table.field_names = ["№", self.cfg["title"]["list"]]
        self.table.align = "l"
        for key, value in self.menu.items():
            self.table.add_row([key + ".", value[0]])
        print(self.table)
        number = input(self.cfg["title"]["choosing"])
        self.menu[number][1]()
        self.show()

    def choose_day(self):
        year = self.cfg["dir"]["year"]
        days = os.listdir(f"{year}\\{os.listdir(year)[len(os.listdir(year)) - 1]}")
        enum_days = {}
        os.system('cls||clear')
        table = PrettyTable()
        table.field_names = ["№", self.cfg["title"]["days list"]]
        table.align = "l"
        for day in days:
            num = int(day[0:2])
            enum_days[num] = day
            table.add_row([str(num) + ".", day])    
        print(table)
        day = int(input(self.cfg["title"]["choosing"] + " "))
        with open("config.ini", mode="w", encoding="utf-8") as config:
            self.cfg["dir"]["day"] = enum_days[day]
            self.cfg.write(config)

    def log_in(self):
        log_in = auth.Login()
        log_in.log_in()

    def set_types(self):
        payments_parser = parse.PaymentsParser()
        payments_parser.set_types()

    def single_payments(self):
        payments = self.manager.get_unmatched()
        progress_bar = self.manager.get_progress_bar("single")
        while len(payments) > 0:
            with mp.Pool(processes=4) as pool:
                for result in pool.imap_unordered(matching.receipting, payments):
                    if result:
                        progress_bar.next()
            payments = self.manager.get_unmatched()
        progress_bar.finish()

    def regitser_payments(self):
        reg_parser = parse.RegisterParser()
        reg_parser.open_xml_files()
        progress_bar_reg = self.manager.get_progress_bar("register")
        for reg in self.manager.registers:
            matching.reg_receipting(reg)
            progress_bar_reg.next()
        progress_bar_reg.finish()

    def save_report(self):
        cur_report = report.Report()
        cur_report.save_report()


if __name__ == "__main__":
    menu = Menu()
    menu.show()
