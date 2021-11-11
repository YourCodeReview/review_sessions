from aiogram import Bot, types, executor, Dispatcher
from aiogram.types import InlineKeyboardButton, InlineKeyboardMarkup, callback_query
from aiogram.utils import executor
from sqlighter import SQLighter
from Tok1 import token11


bot = Bot(token= token11)
dp = Dispatcher(bot)

logging.basicConfig(level=logging.INFO)

bot = Bot(token=config.API_TOKEN)
dp = Dispatcher(bot)




#@dp.message_handler(content_types=['text'])
#async def answer(message: types.Message):
#     await bot.send_message(message.chat.id, 'Привет, я твой помощник в студенчиской жизни 👨🏻‍🎓\nВыбери чем тебе помочь 😉'

@dp.message_handler(commands=['start'])
async def process_start_command(message: types.Message):
    markupfunc = InlineKeyboardMarkup()
    func1 = InlineKeyboardButton('Расписание пар',callback_data = 'func1')

    markupfunc.add(func1)

    await bot.send_message(message.chat.id, 'Привет, я твой помощник в студенчиской жизни 👨🏻‍🎓\nВыбери чем тебе помочь 😉', reply_markup = markupfunc)


# РАСПИСАНИЕ пар
@dp.callback_query_handler(lambda c: c.data == 'func1')
async def group_but(call: types.callback_query):
    markup = InlineKeyboardMarkup()
    markup.add(types.KeyboardButton(text="Запросить геолокацию", request_location=True))
    but_zav1 = InlineKeyboardButton('РАТК 🗓',callback_data = 'but_zav1')
    but_zav2 = InlineKeyboardButton('ДБК 🗓',callback_data = 'but_zav2')
    
    markup.add(but_zav1,but_zav2)

    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id,'Выберите учебное завидение',reply_markup = markup)

@dp.callback_query_handler(lambda c: c.data == 'but_zav1')
async def poned_but(call: types.callback_query):
    markup_cours = InlineKeyboardMarkup()
    but_c1 = InlineKeyboardButton('1',callback_data = 'but_c1')
    but_c2 = InlineKeyboardButton('2',callback_data = 'but_c2')
    but_c3 = InlineKeyboardButton('3',callback_data = 'but_c3')
    but_c4 = InlineKeyboardButton('4',callback_data = 'but_c4')

    markup_cours.add(but_c1,but_c2,but_c3,but_c4)

    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id,'Выберите курс' ,reply_markup = markup_cours)

# ## РАТК ############################
@dp.callback_query_handler(lambda c: c.data == 'but_c1')
async def group_but(call: types.callback_query):
    markup2 = InlineKeyboardMarkup()
    gr_9 = InlineKeyboardButton('1Т-1',callback_data = 'gr_9')
    gr_10 = InlineKeyboardButton('1Т-2',callback_data = 'gr_10')
    gr_11 = InlineKeyboardButton('1ТК-1',callback_data = 'gr_11')
    gr_12 = InlineKeyboardButton('1ТК-2',callback_data = 'gr_12')
    gr_13 = InlineKeyboardButton('1Э-1',callback_data = 'gr_13')
    gr_14 = InlineKeyboardButton('1Э-2',callback_data = 'gr_14')
    gr_15 = InlineKeyboardButton('1Э-3',callback_data = 'gr_15')
    gr_16 = InlineKeyboardButton('1ЭК-1',callback_data = 'gr_16')
    gr_17 = InlineKeyboardButton('1БУХ',callback_data = 'gr_17')
    gr_18 = InlineKeyboardButton('1С-1',callback_data = 'gr_18')
    gr_19 = InlineKeyboardButton('1БС-1',callback_data = 'gr_19')
    gr_20 = InlineKeyboardButton('1Т-1А',callback_data = 'gr_20')
    
    markup2.add(gr_9,gr_10,gr_11,gr_12,gr_13,gr_14,gr_15,gr_16,gr_17,gr_18,gr_19,gr_20)

    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'Выберите свою группу.\nА я скину тебе расписанеи пар', reply_markup = markup2)

@dp.callback_query_handler(lambda c: c.data == 'but_c2')
async def group_but(call: types.callback_query):
    markup2 = InlineKeyboardMarkup()
    gr_1 = InlineKeyboardButton('2БУХ',callback_data = 'gr_1')
    gr_2 = InlineKeyboardButton('2С-1',callback_data = 'gr_2')
    gr_3 = InlineKeyboardButton('2C-2',callback_data = 'gr_3')
    gr_4 = InlineKeyboardButton('2Т-1а',callback_data = 'gr_4')
    gr_21 = InlineKeyboardButton('2Т-1',callback_data = 'gr_21')
    gr_22 = InlineKeyboardButton('2Т-2',callback_data = 'gr_22')
    gr_23 = InlineKeyboardButton('2ТК-1',callback_data = 'gr_23')
    gr_24 = InlineKeyboardButton('2ТК-2',callback_data = 'gr_24')
    gr_25 = InlineKeyboardButton('2Э-1',callback_data = 'gr_25')
    gr_26 = InlineKeyboardButton('2Э-2',callback_data = 'gr_26')
    gr_27 = InlineKeyboardButton('2Э-3',callback_data = 'gr_27')
    gr_28 = InlineKeyboardButton('2ЭК-1',callback_data = 'gr_28')

    
    markup2.add(gr_1,gr_2,gr_3,gr_4,gr_21,gr_22,gr_23,gr_24,gr_25,gr_26,gr_27,gr_28)

    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'Выберите свою группу.\nА я скину тебе расписанеи пар', reply_markup = markup2)

@dp.callback_query_handler(lambda c: c.data == 'but_c3')
async def group_but(call: types.callback_query):
    markup2 = InlineKeyboardMarkup()
    gr_5 = InlineKeyboardButton('3Т-1',callback_data = 'gr_5')
    gr_6 = InlineKeyboardButton('3Т-2',callback_data = 'gr_6')
    gr_7 = InlineKeyboardButton('3ТК-1',callback_data = 'gr_7')
    gr_8 = InlineKeyboardButton('3ТК-2',callback_data = 'gr_8')
    gr_29 = InlineKeyboardButton('3Э-1',callback_data = 'gr_29')
    gr_30 = InlineKeyboardButton('3Э-2',callback_data = 'gr_30')
    gr_31 = InlineKeyboardButton('3Э-3',callback_data = 'gr_31')
    gr_32 = InlineKeyboardButton('3ЭK-1',callback_data = 'gr_32')
    gr_33 = InlineKeyboardButton('3БУХ',callback_data = 'gr_33')
    gr_34 = InlineKeyboardButton('3бух/к',callback_data = 'gr_34')
    gr_35 = InlineKeyboardButton('3С-1',callback_data = 'gr_35')
    gr_36 = InlineKeyboardButton('3Т-1а',callback_data = 'gr_36')

    markup2.add(gr_5,gr_6,gr_7,gr_8,gr_29,gr_30,gr_31,gr_32,gr_33,gr_34,gr_35,gr_36)

    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'Выберите свою группу.\nА я скину тебе расписанеи пар', reply_markup = markup2)

@dp.callback_query_handler(lambda c: c.data == 'but_c4')
async def group_but(call: types.callback_query):
    markup2 = InlineKeyboardMarkup()
    gr_37 = InlineKeyboardButton('4Т-1',callback_data = 'gr_37')
    gr_38 = InlineKeyboardButton('4Т-2',callback_data = 'gr_38')
    gr_39 = InlineKeyboardButton('4ТК-1',callback_data = 'gr_39')
    gr_40 = InlineKeyboardButton('4ТК-2',callback_data = 'gr_40')
    gr_41 = InlineKeyboardButton('4Э-1',callback_data = 'gr_41')
    gr_42 = InlineKeyboardButton('4Э-2',callback_data = 'gr_42')
    gr_43 = InlineKeyboardButton('4Э-3',callback_data = 'gr_43')

    markup2.add(gr_37,gr_38,gr_39,gr_40,gr_41,gr_42,gr_43)

    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'Выберите свою группу.\nА я скину тебе расписанеи пар', reply_markup = markup2)

#2 БУХ
@dp.callback_query_handler(lambda c: c.data == 'gr_1')
async def poned_but(call: types.callback_query):

    markup3 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup3.add(but_1,but_2,but_3,but_4,but_5)

    #await bot.send_message(message.chat.id, 'На какой день недели?', reply_markup = markup)

    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup3)


    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Маркетинг/математика (09:00-10:30)\n\nРусский (10:45-12:15)\n\nМаркетинг (12:45-14:15)\n\nПсихология общения (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Основы бухучёта (09:00-10:30)\n\nМатематика (10:45-12:15)\n\nОсновы бухучёта (12:45-14:15)\n\nСтатистика/Основы бухучёта (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра (09:00-10:30)\n\nРусский (10:45-12:15)\n\nИстория (12:45-14:15)\n\nИн.язык (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'ДОУ (09:00-10:30)/ко второй\n\nБЖ (10:45-12:15)\n\nФиз-ра (12:45-14:15)\n\nМатематика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'БЖ (09:00-10:30)\n\nСтатистика (10:45-12:15)\n\nДОУ (12:45-14:15)')

#2С -1
@dp.callback_query_handler(lambda c: c.data == 'gr_2')
async def poned_but(call: types.callback_query):

    markup3 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup3.add(but_1,but_2,but_3,but_4,but_5)

    #await bot.send_message(message.chat.id, 'На какой день недели?', reply_markup = markup)

    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup3)

    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Математика (09:00-10:30)\n\nИн.язык (10:45-12:15)\n\nИстория (12:45-14:15)\n\nМаркетинг (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Тарифное Регулирование (09:00-10:30)\n\nТехнология взаиморасчётов (10:45-12:15)\n\nМаркетинг (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Русский/Сервис.деятельность (09:00-10:30)\n\nФиз-ра (10:45-12:15)\n\nТехнология бронирования (12:45-14:15)\n\nТарифное Регулирование (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Технология взаиморасчётов (09:00-10:30)\n\nТехнология бронирования (10:45-12:15)\n\nСервис.деятельность (12:45-14:15)\n\nМатематика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Маркетинг (09:00-10:30)\n\nРусский (10:45-12:15)\n\nИстория (12:45-14:15)')

#2C-2
@dp.callback_query_handler(lambda c: c.data == 'gr_3')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)

    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'История (09:00-10:30)\n\nРусский (10:45-12:15)\n\nРусский/Сервис деятельность (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Маркетинг (09:00-10:30)\n\nСервисная деятельность (10:45-12:15)\n\nФиз-ра (12:45-14:15)\n\nТехнология бронирования (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Маркетинг (09:00-10:30)\n\nТариф.Регулирование (10:45-12:15)\n\nТехнология взаиморасчётов (12:45-14:15)\n\nМаркетинг (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Тариф.Регулирование (09:00-10:30)\n\nМатематика (10:45-12:15)\n\nТехнология бронирования (12:45-14:15)\n\nМаркетинг (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'История (09:00-10:30)\n\nИн.язык (10:45-12:15)\n\nТехнология взаиморасчётов (12:45-14:15)  ')

# 2Т-1а
@dp.callback_query_handler(lambda c: c.data == 'gr_4')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Инф.Технологии (09:00-10:30)\n\nМетрополия (10:45-12:15)\n\nПДД (12:45-14:15)\n\nМетроподия\ПДД (14:30 - 16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'ТО автомобилей \ Основы философии (09:00-10:30)\n\nТО автомобилей (10:45-12:15)\n\nПДД (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Метрополия (09:00-10:30)\n\nОсновы философии (10:45-12:15)\n\nПДД (12:45-14:15)\n\nБЖ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nТО автомобилей (10:45-12:15)\n\nТО автомобилей (10:45-12:15)\n\nФиз-ра (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Инф.технологии \ ко второй\n\n-\n\nОсновы философии(10:45-12:15)\n\nБЖ (12:45-14:15)')

# 3Т-1
@dp.callback_query_handler(lambda c: c.data == 'gr_5')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Инф.Технологии (09:00-10:30)\n\nМетрополия (10:45-12:15)\n\nПДД (12:45-14:15)\n\nМетроподия\ПДД (14:30 - 16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'ТО автомобилей \ Основы философии (09:00-10:30)\n\nТО автомобилей (10:45-12:15)\n\nПДД (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Метрополия (09:00-10:30)\n\nОсновы философии (10:45-12:15)\n\nПДД (12:45-14:15)\n\nБЖ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра\n\nТО автомобилей (10:45-12:15)\n\nТО автомобилей (10:45-12:15)\n\nИн.язык (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Инф.технологии \ ко второй\n\n-\n\nОсновы философии(10:45-12:15)\n\nБЖ (12:45-14:15)')

# 3Т-2
@dp.callback_query_handler(lambda c: c.data == 'gr_6')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Инф.Технологии (09:00-10:30)\n\nМетрополия (10:45-12:15)\n\nПДД (12:45-14:15)\n\nМетроподия\ПДД (14:30 - 16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'ТО автомобилей \ Основы философии (09:00-10:30)\n\nТО автомобилей (10:45-12:15)\n\nПДД (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Метрополия (09:00-10:30)\n\nОсновы философии (10:45-12:15)\n\nПДД (12:45-14:15)\n\nБЖ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра (09:00-10:30)\n\nТО автомобилей (10:45-12:15)\n\nТО автомобилей (10:45-12:15)\n\nИн.язык (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Инф.технологии (09:00-10:30)\ ко второй\n\n-\n\nОсновы философии(10:45-12:15)\n\nБЖ (12:45-14:15)')

# 3ТК-1
@dp.callback_query_handler(lambda c: c.data == 'gr_7')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Инф.Технологии (09:00-10:30)\n\nМетрополия (10:45-12:15)\n\nПДД (12:45-14:15)\n\nМетроподия\ПДД (14:30 - 16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'ТО автомобилей \ Основы философии (09:00-10:30)\n\nТО автомобилей (10:45-12:15)\n\nПДД (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Метрополия (09:00-10:30)\n\nОсновы философии (10:45-12:15)\n\nПДД (12:45-14:15)\n\nБЖ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра (09:00-10:30)\n\nТО автомобилей (10:45-12:15)\n\nТО автомобилей (10:45-12:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Инф.технологии (09:00-10:30)\ ко второй\n\n-\n\nОсновы философии(10:45-12:15)\n\nБЖ (12:45-14:15)')

# 3ТК-2
@dp.callback_query_handler(lambda c: c.data == 'gr_8')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Инф.Технологии (09:00-10:30)\n\nМетрополия (10:45-12:15)\n\nПДД (12:45-14:15)\n\nМетроподия\ПДД (14:30 - 16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'ТО автомобилей \ Основы философии (09:00-10:30)\n\nТО автомобилей (10:45-12:15)\n\nПДД (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Метрополия (09:00-10:30)\n\nОсновы философии (10:45-12:15)\n\nПДД (12:45-14:15)\n\nБЖ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра (09:00-10:30)\n\nТО автомобилей (10:45-12:15)\n\nТО автомобилей (10:45-12:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Инф.технологии (09:00-10:30)\ ко второй\n\n-\n\nОсновы философии(10:45-12:15)\n\nБЖ (12:45-14:15)')

# 1Т-1
@dp.callback_query_handler(lambda c: c.data == 'gr_9')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физика (09:00-10:30)\n\nФиз-ра (10:45-12:15)\n\nИн.язык(12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Литература \ Физика (09:00-10:30)\n\nИнформатика (10:45-12:15)\n\nХимия (12:45-14:15)\n\nМетематика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'История (09:00-10:30)\n\nЛитература (10:45-12:15)\n\nМатематика (12:45-14:15)\n\nИн.язык\Фи-зра (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Обществознание (09:00-10:30)\n\nАстрономия (10:45-12:15)\n\nХимия (10:45-12:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Русский (09:00-10:30)\n\n-\n\nИнформатика(10:45-12:15)\n\nМатематика (12:45-14:15)\n\n\Химия \ -')

# 1Т-2
@dp.callback_query_handler(lambda c: c.data == 'gr_10')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Химия\ко второй (09:00-10:30)\n\nФизика (10:45-12:15)\n\nФиз-ра(12:45-14:15)\n\nИнформатика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Химия(09:00-10:30)\n\nИнстория (10:45-12:15)\n\nМатематика (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nРусский (10:45-12:15)\n\nМатематика (12:45-14:15)\n\nФи-зра\Ин.язык (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физика\Литература (09:00-10:30)\n\nХимия (10:45-12:15)\n\nРусский(10:45-12:15)\n\nФизика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Астрономия (09:00-10:30)\n\n-\n\nЛитератруа(10:45-12:15)\n\nИн.язык (12:45-14:15)\n\nМатематика (14:30-16:00)')

# 1ТК-1
@dp.callback_query_handler(lambda c: c.data == 'gr_11')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nИн.яз (10:45-12:15)\n\nМатематика (12:45-14:15)\n\nХимия (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Литература (09:00-10:30)\n\nМатематика (10:45-12:15)\n\nИстория (12:45-14:15)\n\nАстрономия (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ин.яз \ Физ-ра (09:00-10:30)\n\nМатематика (10:45-12:15)\n\nИнформатика (12:45-14:15)\n\nОбществознание (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Литература\Физика (09:00-10:30)\n\nХимия (10:45-12:15)\n\nРусский(10:45-12:15)\n\nФизика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\Химия\n\n-\n\nФиз-ра(10:45-12:15)\n\nИнформатика (12:45-14:15)')

# 1ТК-2
@dp.callback_query_handler(lambda c: c.data == 'gr_12')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра\Химия (09:00-10:30)\n\nИнформатика (10:45-12:15)\n\nФиз-ра (12:45-14:15)\n\nИн.яз (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физика\Литература (09:00-10:30)\n\nХимия (10:45-12:15)\n\nРусский(12:45-14:15)\n\nОбществознание(14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Математика (09:00-10:30)\n\nИнформатика(10:45-12:15)\n\nФизика (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'История (09:00-10:30)\n\nМатематика (10:45-12:15)\n\nЛитература (10:45-12:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Математика (09:00-10:30)\n\nХимия (10:45-12:15)\n\nАстрономия (12:45-14:15)\n\nИн.язык \ - (14:30-16:00)')

# 1Э-1
@dp.callback_query_handler(lambda c: c.data == 'gr_13')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nПраво (10:45-12:15)\n\nМатематика (12:45-14:15)\n\nГеография (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра\Информатика (09:00-10:30)\n\nРусский (10:45-12:15)\n\nИнформатика (12:45-14:15)\n\nПраво (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Технология (09:00-10:30)\n\nФиз-ра(10:45-12:15)\n\Обществознание (12:45-14:15)\n\nМатематика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '- \ Ин.язык (09:00-10:30)\n\nИстория (10:45-12:15)\n\nМатематика (10:45-12:15)\n\nЛитература (14:30-16:00) ')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'География \ Литература (09:00-10:30)\n\nАстрономия\n\nИн.язык(10:45-12:15)\n\n- \ Право (12:45-14:15)')

# 1Э-2
@dp.callback_query_handler(lambda c: c.data == 'gr_14')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'География \ Литература (09:00-10:30)\n\nМатематика (10:45-12:15)\n\nРусский (12:45-14:15)\n\n')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Право (09:00-10:30)\n\nИстория (10:45-12:15)\n\nОбщество (12:45-14:15)\n\nТехнология (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Информатика \ Физ-ра (09:00-10:30)\n\nИн.язык (10:45-12:15)\n\nГеография (12:45-14:15)\n\nМатематика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Право (09:00-10:30)\n\nФиз-ра (10:45-12:15)\n\nАстрономия (10:45-12:15)\n\nИнформатика (14:30-16:00) ')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Право \ Ин.язык (09:00-10:30)\n\nМатематика\n\nЛитература (10:45-12:15)')

# 1Э-3
@dp.callback_query_handler(lambda c: c.data == 'gr_15')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Литература \ География (09:00-10:30)\n\nМатематика (10:45-12:15)\n\nИнформатика (12:45-14:15)\n\nПраво (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Обществознание (09:00-10:30)\n\nФиз-ра (10:45-12:15)\n\nЛитература (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'История (09:00-10:30)\n\nАстрономия (10:45-12:15)\n\nИн.язык\ (12:45-14:15)\n\nИнформатика \ Право (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nРусский (10:45-12:15)\n\nМатематика (10:45-12:15)\n\nПраво (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ин.язык \ Физ-ра (09:00-10:30)\n\nГеография\n\nТехнология (10:45-12:15)')

# 1ЭК-1
@dp.callback_query_handler(lambda c: c.data == 'gr_16')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nРусский (10:45-12:15)\n\nФиз-ра (12:45-14:15)\n\nМатематика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nОбществознание (10:45-12:15)\n\nИнформатика (12:45-14:15)\n\nИнформатика \ Литература (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра \ Ин.язык (09:00-10:30)\n\nГеография (10:45-12:15)\n\Литература (12:45-14:15)\n\nАстрономия (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Право (09:00-10:30)\n\nТехнология (10:45-12:15)\n\nИстория (10:45-12:15)\n\nИн.язык (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Право \ География (09:00-10:30)\n\nМатематика\n\nПраво\n\nМатематика (10:45-12:15)')

# 1БУХ
@dp.callback_query_handler(lambda c: c.data == 'gr_17')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Литература (09:00-10:30) \ -\n\nГеография (10:45-12:15)\n\nОбщество (12:45-14:15)\n\nМатематика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Право (09:00-10:30)\n\nАстрономия (10:45-12:15)\n\nФиз-ра (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'География \ Информатика (09:00-10:30)\n\nМатематика (10:45-12:15)\n\История (12:45-14:15)\n\nИн.язык \ Право (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Информатика (09:00-10:30)\n\nРусский (10:45-12:15)\n\nТехнология (10:45-12:15)\n\nПраво (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра (09:00-10:30)\ -\n\nИн.язык\n\nМатематика\n\nЛитература (10:45-12:15)')

# 1С-1'
@dp.callback_query_handler(lambda c: c.data == 'gr_18')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ин.язык (09:00-10:30)\n\nФиз-ра (10:45-12:15)\n\nМатематика (12:45-14:15)\n\nТехнология (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Информатика \ Физ-ра (09:00-10:30)\n\nРусский (10:45-12:15)\n\nИстория (12:45-14:15)Право (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Астрономия (09:00-10:30)\n\nМатематика (10:45-12:15)\n\nЛитература (12:45-14:15)\n\nПраво \ География (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ин.язык \ Литература (09:00-10:30)\n\nИнформатика (10:45-12:15)\n\nОбществознание (10:45-12:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Математика (09:00-10:30)\n\nПраво\n\nМатематика\n\nГеография (10:45-12:15)')

# 1БС-1'
@dp.callback_query_handler(lambda c: c.data == 'gr_19')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '- \ Физ-ра (09:00-10:30)\n\nПраво (10:45-12:15)\n\nГеография (12:45-14:15)\n\nЛитература (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Математика \ Физ-ра (09:00-10:30)\n\nАстрономия (10:45-12:15)\n\nИнформатика (12:45-14:15)Право (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Информатика (09:00-10:30)\ - \n\nОбществознание (10:45-12:15)\n\nМатематика (12:45-14:15)\n\nМатематика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Право \ Литература (09:00-10:30)\n\nИн.язык (10:45-12:15)\n\nПраво (10:45-12:15)\n\nИстория (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'География (09:00-10:30)\ -\n\nТехнология\n\nРусский\n\n- \ Ин.язык (10:45-12:15)')

# 1Т-1А'
@dp.callback_query_handler(lambda c: c.data == 'gr_20')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ремонт двигателей (09:00-10:30)\n\nФиз-ра (10:45-12:15)\n\nЭлектротехника (12:45-14:15)\n\nТИнженерная графика \ - (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Устройство Автомобмлей (09:00-10:30)\n\nМатематика (10:45-12:15)\n\nИн.язык (12:45-14:15)\n\nРемон двигателей (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Математика (09:00-10:30)\n\nТехническая механника (10:45-12:15)\n\nИстория (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'ТО и Ремон шасси (09:00-10:30)\n\nТО и Ремон шасси (10:45-12:15)\n\nУстройство автомобмлей (10:45-12:15)\n\nТО и Ремон шасси \ - (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Инжинерная графика (09:00-10:30)\n\nТехническая механника\n\nЭлектротехника\n\nИстория (10:45-12:15)')

# 2Т-1'
@dp.callback_query_handler(lambda c: c.data == 'gr_21')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Электотезника (09:00-10:30)\n\nТехническая механника (10:45-12:15)\n\nУстройство авто (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Инжинерная графика (09:00-10:30)\n\nВыполнение работ по профессии (10:45-12:15)\n\nФиз-ра (12:45-14:15)\n\nЭлектротехника (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Математика (09:00-10:30)\n\nИн.язык (10:45-12:15)\n\nТехническая механника(12:45-14:15)\n\nАЭМ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Устройство авто (09:00-10:30)\n\nАЭМ(10:45-12:15)\n\nМатематика (10:45-12:15)\n\nУстройство авто \ Электротехника (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Выполнение работ по профессии \ Техническая механника (09:00-10:30)\n\nИнжинерная графика (10:45-12:15)\n\nУстройство авто (12:45-14:15)')

# 2Т-2'
@dp.callback_query_handler(lambda c: c.data == 'gr_22')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Техническая механника \ Электротехника \n\nМатематика (10:45-12:15)\n\nАЭМ (12:45-14:15)\n\nТехническая механника (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Устройство автомобмлей (09:00-10:30)\n\nФиз-ра (10:45-12:15)\n\nВыполнение работ по профессии (12:45-14:15)\n\nИнжинерная графика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Электротехника (09:00-10:30)\n\nУстройство автомобилей (10:45-12:15)\n\nИнжинерная графика(12:45-14:15)\n\nУстройство автомобилей (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nТехническая механника(10:45-12:15)\n\nИн.язык (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nУстройство автомобилей \ Выполнение работ по профессии (10:45-12:15)\n\nМатематика (10:45-12:15)\n\nАЭМ (12:45-14:15)\n\nЭлектотехника (14:30-16:00)')

# 2ТК-1
@dp.callback_query_handler(lambda c: c.data == 'gr_23')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '- \ Техническая механника\n\nИнжинерная графика (10:45-12:15)\n\nИн.язык (12:45-14:15)\n\nЭлектро техника (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Выполение работ по профессии \ Устройство авто (09:00-10:30)\n\nУстройство авто (10:45-12:15)\n\nМатематика (12:45-14:15)\n\nАЭМ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Техническая механника (09:00-10:30)\n\nАЭМ (10:45-12:15)\n\nЭлектротехника (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Устройство автомобилей (09:00-10:30)\n\nУстройство автомобилей (10:45-12:15)\n\nФиз-ра (12:45-14:15)\n\nМатематика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Электротехника \ - (09:00-10:30)\n\nВыполнениеработ по профессии (10:45-12:15)\n\nТехническая механника (12:45-14:15)\n\nИнжинерная графика (14:30-16:00)')

# 2ТК-2
@dp.callback_query_handler(lambda c: c.data == 'gr_24')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nЭлектротехника (10:45-12:15)\n\nИнжинерная графика (12:45-14:15)\n\nАЭМ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, ' Электотехника \ Выполение работ по профессии (09:00-10:30)\n\nАЭМ (10:45-12:15)\n\nУстройство авто (12:45-14:15)\n\nМатематика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Устрйство авто (09:00-10:30)\n\nЭлектротехника (10:45-12:15)\n\nИн.язык (12:45-14:15) \n\nТехническая механника (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Техническая механника (09:00-10:30)\n\nИнжинерная графика (10:45-12:15)\n\nФиз-ра (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Техническая механника \ Устройство автомобмлей (09:00-10:30)\n\nМатематика (10:45-12:15)\n\nВыполнениеработ по профессии (12:45-14:15)\n\nУстройство авто (14:30-16:00)')

# 2Э-1 
@dp.callback_query_handler(lambda c: c.data == 'gr_25')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Математика \ Технические средства\n\nТехнология перевозок (10:45-12:15)\n\nРусский (12:45-14:15)\n\nИстория (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, ' Математика (09:00-10:30)\n\nТехнические средства (10:45-12:15)\n\nИнжинерная графика (12:45-14:15)\n\nРусский \ История (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Инжинерная графика \ - (09:00-10:30)\n\nФиз-ра (10:45-12:15)\n\nТехнология перевозок (12:45-14:15) \n\nЭлектротехника \ - (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Математика (09:00-10:30)\n\nТранспортная система России (10:45-12:15)\n\nИн.язык (12:45-14:15)\n\nИнжинерная графика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, ' -\n\nЭлектротехника (10:45-12:15)\n\nТранспортная система России (12:45-14:15)\n\nТехнология перевозок (14:30-16:00)')

# 2Э-2 
@dp.callback_query_handler(lambda c: c.data == 'gr_26')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Технология перевозок (09:00-10:30)\n\nТехнические средства (10:45-12:15)\n\nИн.язык (12:45-14:15)\n\nРусский (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Технические средства \ История (09:00-10:30)\n\nИнжинерная графика (10:45-12:15)\n\nЭлектротехника (12:45-14:15)\n\nМатематика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nТехнология перевозок (10:45-12:15)\n\nТранспортная система России (12:45-14:15)\n\nИнжинерная графика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Русский \ Инжинерная графика (09:00-10:30)\n\nМатематика (10:45-12:15)\n\nТранспортная система России (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, ' Математика \ Электротехника (09:00-10:30)\n\nФиз-ра (10:45-12:15)\n\nТехнология перевозок (12:45-14:15)\n\nИстория (14:30-16:00)')

# 2Э-3 
@dp.callback_query_handler(lambda c: c.data == 'gr_27')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Инжинерная графика (09:00-10:30)\n\nИстория (10:45-12:15)\n\nТехнические средства (12:45-14:15)\n\n- \ Инжинерная графика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'История \ Технические средства (09:00-10:30)\n\nЭлектротехника (10:45-12:15)\n\nМатематика (12:45-14:15)\n\nМатематика (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Технология перевозок (09:00-10:30)\n\nТранспортная система России (10:45-12:15)\n\nРусский (12:45-14:15)\n\n- \ Электотехника (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Математика \ - (09:00-10:30)\n\nФиз-ра (10:45-12:15)\n\nИн.язык (12:45-14:15)\n\nТехнология перевозок (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Русский \ - (09:00-10:30)\n\nТехнология перевозок (10:45-12:15)\n\nИнжинерная графика (12:45-14:15)\n\nТранспортная система России (14:30-16:00)')

# 2ЭК-1
@dp.callback_query_handler(lambda c: c.data == 'gr_28')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Техничесские средства \ Русский (09:00-10:30)\n\nТехнология перевозочного пр-са (10:45-12:15)\n\nТехнология перевозочного пр-са (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Транспортная система России (09:00-10:30)\n\nЭлектротехника (10:45-12:15)\n\nТехничесскеи средства (12:45-14:15)\n\nИн.язык (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '- \ Инжинерная графика (09:00-10:30)\n\nИнжинерная графика (10:45-12:15)\n\nфиз-ра (12:45-14:15)\n\nИстория \ Электротехника (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Математика (09:00-10:30)\n\nТехнология перевозочного пр-са (10:45-12:15)\n\nИнжинерная графика (12:45-14:15)\n\nТранспортная система России (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '- \ Математика (09:00-10:30)\n\nИстория (10:45-12:15)\n\nРусский (12:45-14:15)\n\nМатематика (14:30-16:00)')

# 3Э-1
@dp.callback_query_handler(lambda c: c.data == 'gr_29')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра (09:00-10:30)\n\nПДД (10:45-12:15)\n\nВыполнение работ по профессии (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Основы философии \ - (09:00-10:30)\n\nОрганизация движения \ Организация пасажирских перевозок (10:45-12:15)\n\nОрганизация движения (12:45-14:15)\n\nПДД (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Выполнение работ по професии \ - (09:00-10:30)\n\nДОУ (10:45-12:15)\n\nОсновы предпринимательской деятельности (12:45-14:15)\n\nПДД (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Организация движения (09:00-10:30)\n\nОрганизация пассажирских перевозок (10:45-12:15)\n\nОрганизация пассажирских перевозок (12:45-14:15)\n\nДОУ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Основы предпринимательской деятельности (09:00-10:30)\n\nВыполнение работ по профессии (10:45-12:15)\n\Основы философии (12:45-14:15)\n\nИн.язык (14:30-16:00)')

# 3Э-2
@dp.callback_query_handler(lambda c: c.data == 'gr_30')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ин.язык (09:00-10:30)\n\nПДД (10:45-12:15)\n\nВыполнение работ по профессии (12:45-14:15)\n\nФиз-ра (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Основы философии \ - (09:00-10:30)\n\nОрганизация движения \ Организация пасажирских перевозок (10:45-12:15)\n\nОрганизация движения (12:45-14:15)\n\nПДД (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Выполнение работ по професии \ - (09:00-10:30)\n\nДОУ (10:45-12:15)\n\nОсновы предпринимательской деятельности (12:45-14:15)\n\nПДД (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Организация движения (09:00-10:30)\n\nОрганизация пассажирских перевозок (10:45-12:15)\n\nОрганизация пассажирских перевозок (12:45-14:15)\n\nДОУ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Основы предпринимательской деятельности (09:00-10:30)\n\nВыполнение работ по профессии (10:45-12:15)\n\Основы философии (12:45-14:15)')

# 3Э-3
@dp.callback_query_handler(lambda c: c.data == 'gr_31')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nПДД (10:45-12:15)\n\nВыполнение работ по профессии (12:45-14:15)\n\nИн.язык (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Основы философии \ - (09:00-10:30)\n\nОрганизация движения \ Организация пасажирских перевозок (10:45-12:15)\n\nОрганизация движения (12:45-14:15)\n\nПДД (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Выполнение работ по професии \ - (09:00-10:30)\n\nДОУ (10:45-12:15)\n\nОсновы предпринимательской деятельности (12:45-14:15)\n\nПДД (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Организация движения (09:00-10:30)\n\nОрганизация пассажирских перевозок (10:45-12:15)\n\nОрганизация пассажирских перевозок (12:45-14:15)\n\nДОУ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Основы предпринимательской деятельности (09:00-10:30)\n\nВыполнение работ по профессии (10:45-12:15)\n\Основы философии (12:45-14:15)\n\nФиз-ра (14:30-16:00)')

# 3ЭK-1
@dp.callback_query_handler(lambda c: c.data == 'gr_32')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nПДД (10:45-12:15)\n\nВыполнение работ по профессии (12:45-14:15)\n\nИн.язык (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Основы философии \ - (09:00-10:30)\n\nОрганизация движения \ Организация пасажирских перевозок (10:45-12:15)\n\nОрганизация движения (12:45-14:15)\n\nПДД (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Выполнение работ по професии \ - (09:00-10:30)\n\nДОУ (10:45-12:15)\n\nОсновы предпринимательской деятельности (12:45-14:15)\n\nПДД (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Организация движения (09:00-10:30)\n\nОрганизация пассажирских перевозок (10:45-12:15)\n\nОрганизация пассажирских перевозок (12:45-14:15)\n\nДОУ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Основы предпринимательской деятельности (09:00-10:30)\n\nВыполнение работ по профессии (10:45-12:15)\n\Основы философии (12:45-14:15)\n\nФиз-ра (14:30-16:00)')

# 3БУХ
@dp.callback_query_handler(lambda c: c.data == 'gr_33')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nТехно провед инвентаря (10:45-12:15)\n\nПОПД (12:45-14:15)\n\nФиз-ра (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ин.язык (09:00-10:30)\n\nПракт.основы бух.учёта (10:45-12:15)\n\nТехн.составления бух.отчётности (12:45-14:15)\n\nАСОЭИ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Тех.состав.бух.отчётности (09:00-10:30)\n\nАСОЭИ (10:45-12:15)\n\nНалоги и налогооблаж \ Организация расчётов (12:45-14:15)\n\nОсновы анализа бух.отчётности (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '- \ ПОПД (09:00-10:30)\n\nАудит (10:45-12:15)\n\nНалоги и налооблаж (12:45-14:15)\n\nОрганизация расчётов (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Тех.состав.бух.отчётности \ Основы анализа бух.отчётности (09:00-10:30)\n\nФДК (10:45-12:15)\n\Тех.состав.бух.отчётности (12:45-14:15)\n\nПракт.основы бух.учёта \ ФДК (14:30-16:00)')


# 3бух/к
@dp.callback_query_handler(lambda c: c.data == 'gr_34')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ин.язык (09:00-10:30)\n\nТехно провед инвентаря (10:45-12:15)\n\nПОПД (12:45-14:15)\n\nФиз-ра (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nПракт.основы бух.учёта (10:45-12:15)\n\nТехн.составления бух.отчётности (12:45-14:15)\n\nАСОЭИ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Тех.состав.бух.отчётности (09:00-10:30)\n\nАСОЭИ (10:45-12:15)\n\nНалоги и налогооблаж \ Организация расчётов (12:45-14:15)\n\nОсновы анализа бух.отчётности (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '- \ ПОПД (09:00-10:30)\n\nАудит (10:45-12:15)\n\nНалоги и налооблаж (12:45-14:15)\n\nОрганизация расчётов (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Тех.состав.бух.отчётности \ Основы анализа бух.отчётности (09:00-10:30)\n\nФДК (10:45-12:15)\n\nТех.состав.бух.отчётности (12:45-14:15)\n\nПракт.основы бух.учёта \ ФДК (14:30-16:00)')


# 3С-1
@dp.callback_query_handler(lambda c: c.data == 'gr_35')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'ПОПД \ Основы предпринимательской деятельности (09:00-10:30)\n\nОрганизация Сервиса (10:45-12:15)\n\nМетрология (12:45-14:15)\n\nОрганизация сервисной деятельности (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Риски и страхования на АТ \ Экономика отрасли (09:00-10:30)\n\nПОПД (10:45-12:15)\n\nЭкономика отрасли (12:45-14:15)\n\nОрганизация сервиса (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Основы предпринимательской деятельности (09:00-10:30)\n\nОрганизация безопасности на АТ (10:45-12:15)\n\nОрганизация безопасности на АТ (12:45-14:15)\n\nОрганизация сервисной деятельности (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Организация сервиса (09:00-10:30)\n\nИн.язык (10:45-12:15)\n\nФиз-ра (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nОрганизация безопасности на АТ (10:45-12:15)\n\nОрганизация безопасности на АТ (12:45-14:15)\n\nРиски и страхования на АТ (14:30-16:00)')

# 3Т-1а
@dp.callback_query_handler(lambda c: c.data == 'gr_36')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Учёт и отчётность (09:00-10:30)\n\nЭкономика организации (10:45-12:15)\n\nЭкономика организации (12:45-14:15)\n\nПОПД \ Менеджмент (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ин.язык (09:00-10:30)\n\nУчёт и отчётность \ Экономика организации (10:45-12:15)\n\nОсновы Предпринимательской деятельности (12:45-14:15)\n\nОрганизация ТО и ТР в АТ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nПОПД (10:45-12:15)\n\nОрганизация ТО и ТР в АТ (12:45-14:15)\n\nАвтоперевозки (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Электронные системы управления двигателем (09:00-10:30)\n\nБережливое производство (10:45-12:15)\n\nМенеджмент (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра (09:00-10:30)\n\nОрганизация ТО и ТР в АТ (10:45-12:15)\n\nОрганизация ТО и ТР в АТ (12:45-14:15)\n\nЭлектронные системы управления двигателем (14:30-16:00)')

# 4Т-1
@dp.callback_query_handler(lambda c: c.data == 'gr_37')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Учёт и отчётность (09:00-10:30)\n\nЭкономика организации (10:45-12:15)\n\nЭкономика организации (12:45-14:15)\n\nПОПД \ Менеджмент (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра (09:00-10:30)\n\nУчёт и отчётность \ Экономика организации (10:45-12:15)\n\nОсновы Предпринимательской деятельности (12:45-14:15)\n\nОрганизация ТО и ТР в АТ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nПОПД (10:45-12:15)\n\nОрганизация ТО и ТР в АТ (12:45-14:15)\n\nАвтоперевозки (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Электронные системы управления двигателем (09:00-10:30)\n\nБережливое производство (10:45-12:15)\n\nМенеджмент (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ин.язык (09:00-10:30)\n\nОрганизация ТО и ТР в АТ (10:45-12:15)\n\nОрганизация ТО и ТР в АТ (12:45-14:15)\n\nЭлектронные системы управления двигателем (14:30-16:00)')

# 4Т-2
@dp.callback_query_handler(lambda c: c.data == 'gr_38')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Учёт и отчётность (09:00-10:30)\n\nЭкономика организации (10:45-12:15)\n\nЭкономика организации (12:45-14:15)\n\nПОПД \ Менеджмент (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ин.язык (09:00-10:30)\n\nУчёт и отчётность \ Экономика организации (10:45-12:15)\n\nОсновы Предпринимательской деятельности (12:45-14:15)\n\nОрганизация ТО и ТР в АТ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nПОПД (10:45-12:15)\n\nОрганизация ТО и ТР в АТ (12:45-14:15)\n\nАвтоперевозки (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Электронные системы управления двигателем (09:00-10:30)\n\nБережливое производство (10:45-12:15)\n\nМенеджмент (12:45-14:15)\n\nФиз-ра (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ин.язык (09:00-10:30)\n\nОрганизация ТО и ТР в АТ (10:45-12:15)\n\nОрганизация ТО и ТР в АТ (12:45-14:15)\n\nЭлектронные системы управления двигателем (14:30-16:00)')


# 4ТК-1
@dp.callback_query_handler(lambda c: c.data == 'gr_39')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Учёт и отчётность (09:00-10:30)\n\nЭкономика организации (10:45-12:15)\n\nЭкономика организации (12:45-14:15)\n\nПОПД \ Менеджмент (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ин.язык (09:00-10:30)\n\nУчёт и отчётность \ Экономика организации (10:45-12:15)\n\nОсновы Предпринимательской деятельности (12:45-14:15)\n\nОрганизация ТО и ТР в АТ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра (09:00-10:30)\n\nПОПД (10:45-12:15)\n\nОрганизация ТО и ТР в АТ (12:45-14:15)\n\nАвтоперевозки (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Электронные системы управления двигателем (09:00-10:30)\n\nБережливое производство (10:45-12:15)\n\nМенеджмент (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nОрганизация ТО и ТР в АТ (10:45-12:15)\n\nОрганизация ТО и ТР в АТ (12:45-14:15)\n\nЭлектронные системы управления двигателем (14:30-16:00)')


# 4ТК-2
@dp.callback_query_handler(lambda c: c.data == 'gr_40')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Учёт и отчётность (09:00-10:30)\n\nЭкономика организации (10:45-12:15)\n\nЭкономика организации (12:45-14:15)\n\nПОПД \ Менеджмент (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ин.язык (09:00-10:30)\n\nУчёт и отчётность \ Экономика организации (10:45-12:15)\n\nОсновы Предпринимательской деятельности (12:45-14:15)\n\nОрганизация ТО и ТР в АТ (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра (09:00-10:30)\n\nПОПД (10:45-12:15)\n\nОрганизация ТО и ТР в АТ (12:45-14:15)\n\nАвтоперевозки (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Электронные системы управления двигателем (09:00-10:30)\n\nБережливое производство (10:45-12:15)\n\nМенеджмент (12:45-14:15)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nОрганизация ТО и ТР в АТ (10:45-12:15)\n\nОрганизация ТО и ТР в АТ (12:45-14:15)\n\nЭлектронные системы управления двигателем (14:30-16:00)')


# 4Э-1
@dp.callback_query_handler(lambda c: c.data == 'gr_41')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра (09:00-10:30)\n\nИнформационное обеспечение перевозочного процесса (10:45-12:15)\n\nЭкономика отрасли (12:45-14:15)\n\nОхрана труда \ ПОПД (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Менеджемент \ АСУ на транспорте (09:00-10:30)\n\nАСУ на транспорте (10:45-12:15)\n\nАСУ на транспорте (12:45-14:15)\n\nИн.язык (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nАСУ на транспорте (10:45-12:15)\n\nЭкономика отрасли (12:45-14:15)\n\nМенеджемент (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Охрана труда (09:00-10:30)\n\nПОПД (10:45-12:15)\n\nИнформационное обеспечение перевозочного процесса (12:45-14:15)\n\nЭкономика отросли (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Организация безопасности движения (09:00-10:30)\n\nИнформационное обеспечение перевозочного процесса (10:45-12:15)\n\nИнформационное обеспечение перевозочного процесса (12:45-14:15)')

# 4Э-2
@dp.callback_query_handler(lambda c: c.data == 'gr_42')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Ин.язык (09:00-10:30)\n\nИнформационное обеспечение перевозочного процесса (10:45-12:15)\n\nЭкономика отрасли (12:45-14:15)\n\nОхрана труда \ ПОПД (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Менеджемент \ АСУ на транспорте (09:00-10:30)\n\nАСУ на транспорте (10:45-12:15)\n\nАСУ на транспорте (12:45-14:15)\n\nФиз-ра (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nАСУ на транспорте (10:45-12:15)\n\nЭкономика отрасли (12:45-14:15)\n\nМенеджемент (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Охрана труда (09:00-10:30)\n\nПОПД (10:45-12:15)\n\nИнформационное обеспечение перевозочного процесса (12:45-14:15)\n\nЭкономика отросли (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Организация безопасности движения (09:00-10:30)\n\nИнформационное обеспечение перевозочного процесса (10:45-12:15)\n\nИнформационное обеспечение перевозочного процесса (12:45-14:15)')

# 4Э-3
@dp.callback_query_handler(lambda c: c.data == 'gr_43')
async def poned_but(call: types.callback_query):

    markup4 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5')
    markup4.add(but_1,but_2,but_3,but_4,but_5)


    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup4)
    
    @dp.callback_query_handler(lambda c: c.data == 'but_1')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nИнформационное обеспечение перевозочного процесса (10:45-12:15)\n\nЭкономика отрасли (12:45-14:15)\n\nОхрана труда \ ПОПД (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Менеджемент \ АСУ на транспорте (09:00-10:30)\n\nАСУ на транспорте (10:45-12:15)\n\nАСУ на транспорте (12:45-14:15)\n\nФиз-ра (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Физ-ра (09:00-10:30)\n\nАСУ на транспорте (10:45-12:15)\n\nЭкономика отрасли (12:45-14:15)\n\nМенеджемент (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Охрана труда (09:00-10:30)\n\nПОПД (10:45-12:15)\n\nИнформационное обеспечение перевозочного процесса (12:45-14:15)\n\nЭкономика отросли (14:30-16:00)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Организация безопасности движения (09:00-10:30)\n\nИнформационное обеспечение перевозочного процесса (10:45-12:15)\n\nИнформационное обеспечение перевозочного процесса (12:45-14:15)')






# РАСПИСАНИЕ практики
@dp.callback_query_handler(lambda c: c.data == 'func2')
async def group_but(call: types.callback_query):
    await bot.send_message(message.chat.id, 'Приносим извинения,этот раздел сейчас недоступен 😢')

# Расписание сессий
@dp.callback_query_handler(lambda c: c.data == 'func3')
async def group_but(call: types.callback_query):
    await bot.send_message(message.chat.id, 'Приносим извинения,этот раздел сейчас недоступен 😢')

# Помощь в рефератах,проектах и тд
@dp.callback_query_handler(lambda c: c.data == 'func4')
async def group_but(call: types.callback_query):
    await bot.send_message(message.chat.id, 'Напишите этому парню ( https://vk.com/den13374 ), он делает качественно и не дорого 👍\nЕсли не получится с ним связаться ,пишите сюда - @EdoDel1 (телеграм) ')




# ## ДБК ############################
@dp.callback_query_handler(lambda c: c.data == 'but_zav2')
async def group_but(call: types.callback_query):
    markup1a = InlineKeyboardMarkup()
    gr_1 = InlineKeyboardButton('БД-24',callback_data = 'gr_1a')
    gr_2 = InlineKeyboardButton('БД-25',callback_data = 'gr_2a')
    gr_3 = InlineKeyboardButton('БД-26',callback_data = 'gr_3a')
    markup1a.add(gr_1,gr_2,gr_3)

    #await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'Выберите свою группу.\nА я скину тебе расписанеи пар', reply_markup = markup1a)

#БД-24
@dp.callback_query_handler(lambda c: c.data == 'gr_1a')
async def poned_but(call: types.callback_query):

    markup3a = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1a')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2a')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3a')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4a')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5a')
    but_6 = InlineKeyboardButton('Пятница',callback_data = 'but_6a')
    markup3a.add(but_1,but_2,but_3,but_4,but_5)

    #await bot.send_message(message.chat.id, 'На какой день недели?', reply_markup = markup)

    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup3a)


    @dp.callback_query_handler(lambda c: c.data == 'but_1a')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Иностранный язык в проф деятельности (08:00-09:35)\n\nИстория(09:55-11:30)\n\nИнформационные технологии в проф деятельности(11:50-13:25)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2a')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nЭлементы высшей метематики (09:55-11:30)\n\nМДК 03.01 ведение кассовых операция (11:50-13:25)\n\nФинансы (13:35 - 15:10)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3a')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id,'Бух учёт (08:00-09:35)\n\nОсновы деятельности кредитных организаций (09:55-11:30)\n\nФиз-ра (11:50-13:25)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4a')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nМДК 03.01 ведение кассовых операция (09:55-11:30)\n\nЭкономика организаций/Элементы высшей математики (11:50-13:25)\n\nФинансы (13:35 - 15:10)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5a')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Экономика организаций (08:00-09:35)\n\nБух учёт (09:55-11:30)\n\nБезопасность жизнидеятельности (11:50-13:25)')

    @dp.callback_query_handler(lambda c: c.data == 'but_6a')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\n-\n\nМДК 03.01 ведение кассовых операция (11:50-13:25)\nФинансы (13:35 - 15:10)')

#БД-25
@dp.callback_query_handler(lambda c: c.data == 'gr_2a')
async def poned_but(call: types.callback_query):

    markup3 = InlineKeyboardMarkup()
    but_1 = InlineKeyboardButton('Понедельник',callback_data = 'but_1a')
    but_2 = InlineKeyboardButton('Вторнк',callback_data = 'but_2a')
    but_3 = InlineKeyboardButton('Среда',callback_data = 'but_3a')
    but_4 = InlineKeyboardButton('Четверг',callback_data = 'but_4a')
    but_5 = InlineKeyboardButton('Пятница',callback_data = 'but_5a')
    but_6 = InlineKeyboardButton('Пятница',callback_data = 'but_6a')
    markup3.add(but_1,but_2,but_3,but_4,but_5)

    #await bot.send_message(message.chat.id, 'На какой день недели?', reply_markup = markup)

    await bot.answer_callback_query(call.id)
    await bot.send_message(call.message.chat.id, 'На какой день  недели?',reply_markup = markup3)


    @dp.callback_query_handler(lambda c: c.data == 'but_1a')
    async def poned_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nМДК 03.01 ведение кассовых операция (09:55-11:30)\n\nИностранный язык в проф деятельности (11:50-13:25)\n\n Информационные технологии в проф деятельности (13:35 - 15:10)')

    @dp.callback_query_handler(lambda c: c.data == 'but_2a')
    async def vt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Элементы высшей метематики(08:00-09:35)\n\nБух учёт (09:55-11:30)\n\nФинансы (11:50 - 13:25)')

    @dp.callback_query_handler(lambda c: c.data == 'but_3a')
    async def sr_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id,'-\n\nФиз-ра (09:55-11:30)\n\nМДК 03.01 ведение кассовых операция (11:50 - 13:25)\n\nБезопасность жизнидеятельности (13:35-15:10)')

    @dp.callback_query_handler(lambda c: c.data == 'but_4a')
    async def cht_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Финансы (08:00-09:35)\n\nОсновы деятельности кред.организаций (11:50-13:25)\n\nЭкономика организаций/Элементы высшей математики(13:35-15:10)')

    @dp.callback_query_handler(lambda c: c.data == 'but_5a')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, '-\n\nЭкономика организаций(09:55-11:30)\n\nФинансы (11:50-13:25)\n\nИстория (13:35-15:10)')

    @dp.callback_query_handler(lambda c: c.data == 'but_6a')
    async def pt_but(call: types.callback_query):
        await bot.answer_callback_query(call.id)
        await bot.send_message(call.message.chat.id, 'Бух учёт (08:00-09:35)\n\nМДК 03.01 ведение кассовых операция (09:55-11:30)')

executor.start_polling(dp)
    