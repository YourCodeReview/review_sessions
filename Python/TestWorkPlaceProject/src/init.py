from json import loads
from sqlite3 import Error
from src.constants import OFFICES_TABLE, USERS_TABLE, USERS_FIELDS_LIST, QUEUE_TABLE

TABLE_CREATE_REQUEST = "CREATE TABLE IF NOT EXISTS {} ({})"
INSERT_REQUEST = "INSERT OR REPLACE INTO {} ({}) VALUES ({})"
TABLE_STRUCTURE_PATH = 'data/table_structure.json'
USER_DATA_FILE = 'data/init_data.json'


def initialize_database(connection):
    """Первоначальное заполнение базы"""
    if isinstance(connection, tuple):
        return False
    else:
        if create_tables(load_tables(), connection):
            users_count = get_users_count(connection)
            if users_count == 0:
                if fill_users_table(load_users(), connection):
                    if clear_tables_data(connection):
                        return True
            else:
                if clear_tables_data(connection):
                    return True
    return False


def create_tables(tables_data, connection):
    """Создадние необходимых таблиц"""
    with connection:
        for table_name in list(tables_data.keys()):
            create_data = ''
            for parameter in tables_data[table_name]:
                if len(create_data) > 0:
                    create_data += ', '
                create_data += '"' + str(parameter).strip() + '"' + ' ' + tables_data[table_name][parameter]
            try:
                connection.execute(TABLE_CREATE_REQUEST.format(table_name, create_data))
            except Error:
                print('Error creating table', table_name)
                return False
    print('tables created')
    return True


def fill_users_table(users, connection):
    """Заполнение таблицы пользователей данными о пользователях"""
    request = INSERT_REQUEST.format(USERS_TABLE, USERS_FIELDS_LIST, '?,?,?,?,?,?')
    data = []
    for user in users:
        new_row = (user['first_name'], user['last_name'], 0, 0, 0, 0)
        data.append(new_row)
    try:
        with connection:
            connection.executemany(request, data)
    except Error:
        print('Error inserting data to table', USERS_TABLE, 'on data: ', data)
        return False
    print('users added')
    return True


def clear_tables_data(connection):
    """Удаление офисов и привязок к ним"""
    try:
        with connection:
            connection.execute(f'DELETE FROM {OFFICES_TABLE}')
            connection.execute(f'UPDATE {USERS_TABLE} SET OFFICE_ID=NULL')
            connection.execute(f'DELETE FROM {QUEUE_TABLE}')
    except Error:
        print('Error clearing tables')
        return False
    print('tables cleared')
    return True


def load_tables():
    """Загрузка данных о структуре таблиц из файла"""
    with open(TABLE_STRUCTURE_PATH) as f:
        tables_data = loads(f.read())
    return tables_data


def load_users():
    """Загрузка данных о пользователях из файла"""
    users = []
    with open(USER_DATA_FILE, 'r', encoding='utf8') as file:
        json_data = loads(file.read())
    for index, person in enumerate(json_data):
        user = person
        users.append(user)
    return users


def get_users_count(connection):
    """Получить список пользователей из базы"""
    result = connection.execute(f'SELECT COUNT(ID) FROM {USERS_TABLE}')
    return result.fetchall()[0][0]
