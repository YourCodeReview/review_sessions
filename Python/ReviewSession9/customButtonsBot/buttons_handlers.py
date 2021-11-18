#-*- coding: utf-8 -*-

from aiogram import types


class ButtonsHandlers:
	def __init__(self, bot, dp, db):
		self.bot = bot
		self.dp = dp
		self.db = db

	def handlers(self):
		@self.dp.message_handler(content_types=['text'])
		async def buttons_handler(message: types.Message):
			cmd = message.text
			buttons = [btn[2] for btn in self.db.get_buttons()]

			if cmd in buttons:
				button = self.db.get_button_with_text(cmd)
				await self.bot.send_message(
						chat_id=message.chat.id,
						text=button[3],
					)
