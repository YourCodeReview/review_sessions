#-*- coding: utf-8 -*-

from aiogram import types


class KeyboardsManager:
    def __init__(self, db):
        self.db = db

    def main(self):
        """
        Главная клавиатура
        :param none
        :return keyboard
        """
        keyboard = {
            "keyboard": [], 
            "resize_keyboard": True,
        }

        template = [
                [], [], [], [],
            ]

        buttons = self.db.get_buttons()
        for button in buttons:
            row = int(button[1]) - 1
            text = button[2]
            template[row].append(text)

        keyboard['keyboard'] = template
        return keyboard

    def edit_buttons(self):
        """
        Клавиатура со всеми существующими кнопками
        :param none
        :return keyboard
        """
        keyboard = types.InlineKeyboardMarkup(row_width=1, resize_keyboard=True)
        buttons = self.db.get_buttons()
        for button in buttons:
            btn = types.InlineKeyboardButton(
                    text=button[2],
                    callback_data=f"admin:buttons:menu:{button[0]}",
                )
            keyboard.add(btn)   

        btn = types.InlineKeyboardButton(
                text="Добавить кнопку",
                callback_data=f"admin:buttons:add",
            )
        keyboard.add(btn)
        return keyboard

    def button_menu(self, id):
        """
        Клавиатура управления кнопкой, удалить/изменить можно с помощью нее
        :param button_id
        :return keyboard
        """      
        keyboard = types.InlineKeyboardMarkup(row_width=1, resize_keyboard=True)

        btn1 = types.InlineKeyboardButton(
                text="Изменить текст",
                callback_data=f"admin:buttons:text:{id}",
            )

        btn2 = types.InlineKeyboardButton(
                text="Изменить сообщение",
                callback_data=f"admin:buttons:msg:{id}",
            )

        btn3 = types.InlineKeyboardButton(
                text="Удалить",
                callback_data=f"admin:buttons:remove:{id}",
            )

        btn4 = types.InlineKeyboardButton(
                text="↩️Назад↩️",
                callback_data=f"admin:buttons:all",
            )

        keyboard.row(btn1, btn2)
        keyboard.add(btn3, btn4)
        return keyboard

