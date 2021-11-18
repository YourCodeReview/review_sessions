#-*- coding: utf-8 -*-

from aiogram import Bot
from aiogram import types
from aiogram.utils import executor
from aiogram.dispatcher import Dispatcher
from aiogram.contrib.fsm_storage.memory import MemoryStorage

from database import DatabaseManager
from keyboards import KeyboardsManager
from dialog_mashine import DialogMashine
from mashine_handlers import MashineHandlers
from callback_handlers import CallbackHandlers
from buttons_handlers import ButtonsHandlers

TOKEN = "1614502276:AAFGR_oVKF89-KyrZDKrO5ryeF24yf8Icro"


class CustomBot:
    def __init__(self, token):
        self.bot = Bot(token)
        self.dp = Dispatcher(self.bot,  storage=MemoryStorage())

        self.db = DatabaseManager()
        self.keyboards = KeyboardsManager(self.db)
        self.dialog_mashine = DialogMashine()

        self.mashine_handlers = MashineHandlers(self.bot, self.dp,
                                                self.db, self.dialog_mashine,
                                                self.keyboards)
        self.callback_handles = CallbackHandlers(self.bot, self.dp,
                                                self.db, self.dialog_mashine,
                                                self.keyboards)
        self.buttons_handlers = ButtonsHandlers(self.bot, self.dp,
                                                self.db)

    def start(self):
        # Главное меню
        @self.dp.message_handler(commands=['start'])
        async def process_start(message: types.Message):
            await self.bot.send_message(
                    chat_id=message.chat.id,
                    text=self.db.get_message_with_id("1"),
                    reply_markup=self.keyboards.main(),
                )

        # Админ панель, для редактирвания элементов
        @self.dp.message_handler(commands=['admin'])
        async def process_admin(message: types.Message):
            admins = self.db.get_admins()
            if str(message.chat.id) in admins:
                await self.bot.send_message(
                        chat_id=message.chat.id,
                        text=self.db.get_message_with_id("2"),
                        reply_markup=self.keyboards.edit_buttons(),
                    )

            else:
                await self.bot.send_message(
                        chat_id=message.chat.id,
                        text=self.db.get_message_with_id("3"),
                    )

        # Процесс добавления нового админа
        @self.dp.message_handler(commands=['addAdmin'])
        async def process_add_admin(message: types.Message):
            admins = self.db.get_admins()
            if str(message.chat.id) in admins:
                await self.bot.send_message(
                        chat_id=message.chat.id,
                        text=self.db.get_message_with_id("4"),
                    )

                await self.dialog_mashine.add_admin.set()

            else:
                await self.bot.send_message(
                        chat_id=message.chat.id,
                        text=self.db.get_message_with_id("3"),
                    )

        # Обработка машины состояний
        self.mashine_handlers.handlers()

        # Обработка callback'ов
        self.callback_handles.handlers()

        # Обоработка нажатий на кнопки
        self.buttons_handlers.handlers()

        executor.start_polling(self.dp, skip_updates=True)


if __name__ == "__main__":
    token = "1614502276:AAFGR_oVKF89-KyrZDKrO5ryeF24yf8Icro"
    bot = CustomBot(token)
    bot.start()