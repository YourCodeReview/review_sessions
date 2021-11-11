#-*- coding: utf-8 -*-

from aiogram.dispatcher.filters.state import State, StatesGroup


class DialogMashine(StatesGroup):
    add_admin = State()

    add_button_step_1 = State()
    add_button_step_2 = State()
    add_button_step_3 = State()

    edit_text = State()
    edit_message = State()