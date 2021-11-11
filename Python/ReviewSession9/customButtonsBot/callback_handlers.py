#-*- coding: utf-8 -*-

from aiogram import types
from aiogram.dispatcher import FSMContext

class CallbackHandlers:
    def __init__(self, bot, dp, db, dialog_mashine, keyboards):
        self.bot = bot
        self.dp = dp
        self.db = db
        self.dialog_mashine = dialog_mashine
        self.keyboards = keyboards

    def handlers(self):
        @self.dp.callback_query_handler(lambda c: c.data)
        async def callback(call: types.CallbackQuery, state: FSMContext):
            await self.bot.answer_callback_query(call.id)
            button = call.data

            if button.split(":")[0] == "admin":
                cmd = button.split(":")[1]

                if cmd == "buttons":
                    cmd = button.split(":")[2]

                    # Все кнопки
                    if cmd == "all":
                        await self.bot.edit_message_text(
                                chat_id=call.message.chat.id,
                                message_id=call.message.message_id,
                                text=self.db.get_message_with_id("6"),
                                reply_markup=self.keyboards.edit_buttons(),
                            )

                    # Меню каждой кнопки
                    elif cmd == "menu":
                        btn_id = button.split(":")[3]
                        await self.bot.edit_message_text(
                                chat_id=call.message.chat.id,
                                message_id=call.message.message_id,
                                text=self.db.get_message_with_id("7"),
                                reply_markup=self.keyboards.button_menu(btn_id),
                            )

                    # Изменение текста на кнопке
                    elif cmd == "text":
                        btn_id = button.split(":")[3]

                        async with state.proxy() as data:
                            data["btn_id"] = btn_id

                        await self.dialog_mashine.edit_text.set()
                        await self.bot.edit_message_text(
                                chat_id=call.message.chat.id,
                                message_id=call.message.message_id,
                                text=self.db.get_message_with_id("8"),
                            )

                    # Изменение сообщения которое отправляет кнопка
                    elif cmd == "msg":
                        btn_id = button.split(":")[3]

                        async with state.proxy() as data:
                            data["btn_id"] = btn_id

                        await self.dialog_mashine.edit_message.set()
                        await self.bot.edit_message_text(
                                chat_id=call.message.chat.id,
                                message_id=call.message.message_id,
                                text=self.db.get_message_with_id("10"),
                            )

                    # Удаление кнопки
                    elif cmd == "remove":
                        btn_id = button.split(":")[3]

                        self.db.remove_button(btn_id)
                        await self.bot.edit_message_text(
                                chat_id=call.message.chat.id,
                                message_id=call.message.message_id,
                                text=self.db.get_message_with_id("12"),
                            )

                    # Добавление кнопки
                    elif cmd == "add":
                        await self.dialog_mashine.add_button_step_1.set()
                        await self.bot.edit_message_text(
                                chat_id=call.message.chat.id,
                                message_id=call.message.message_id,
                                text=self.db.get_message_with_id("13"),
                            )