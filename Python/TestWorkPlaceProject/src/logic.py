from src.constants import OFFICES_TABLE, USERS_TABLE, QUEUE_TABLE, OFFICE_CAPACITY


def get_offices_count(connection):
    """Получение количества офисов"""
    result = connection.execute(f'SELECT COUNT(ID) FROM {OFFICES_TABLE}')
    return result.fetchall()[0][0]


def set_offices_count(connection, offices_count):
    """Установка количества офисов"""
    values = [[x] for x in (range(offices_count))]
    values = [tuple(x) for x in values]
    request = f'INSERT OR REPLACE INTO {OFFICES_TABLE} (Number) VALUES (?)'
    with connection:
        connection.executemany(request, values)


def get_users_in_offices(connection):
    """Получить список пользователей по офисам"""
    result = connection.execute(f'select of.Number, us.First_Name, us.Last_name from {OFFICES_TABLE} Of '
                                f'LEFT JOIN {USERS_TABLE} US on US.office_id=Of.ID '
                                f'ORDER BY Number, US.ID')
    data = result.fetchall()
    ret_result = {x[0]: [] for x in data}
    for line in data:
        if not (line[1] is None and line[2] is None):
            ret_result[line[0]].append((line[1] + ' ' if (line[1] is not None) else '') +
                                       (line[2] if (line[2] is not None) else ''))
    return ret_result


def add_user_to_office(connection, office_id, user_id):
    """Добавить пользователя в офис"""
    with connection:
        connection.execute(f'UPDATE {USERS_TABLE} SET office_id=(select id from offices where Number={office_id}) '
                           f'where id={user_id}')


def remove_user_from_office(connection, user_id):
    """Убрать пользователя из офиса"""
    with connection:
        connection.execute(f'UPDATE {USERS_TABLE} SET OFFICE_ID=NULL WHERE ID={user_id}')


def add_user_to_queue(connection, user_id):
    """Добавить пользователя в очередь"""
    with connection:
        connection.execute(f'INSERT INTO {QUEUE_TABLE} (User_ID) VALUES ({user_id})')


def remove_user_from_queue(connection, user_id):
    """Убрать пользователя из очереди"""
    with connection:
        connection.execute(f'DELETE FROM {QUEUE_TABLE} WHERE User_ID={user_id}')


def set_working_hours(connection, user_id, working_hours):
    """Установить для пользователя количество часов, которые осталось отработать"""
    with connection:
        connection.execute(f'UPDATE {USERS_TABLE} SET Remaining_work_time={working_hours} where ID={user_id}')


def get_user_working_hours(connection, user_id):
    """Установить для пользователя количество часов, которые осталось отработать"""
    with connection:
        result = connection.execute(f'SELECT Remaining_work_time from {USERS_TABLE} where id={user_id}')
    result = result.fetchall()
    return result[0][0]


def set_total_working_hours(connection, user_id, working_hours):
    """Установить для пользователя общее количество часов, которые он отработал"""
    with connection:
        connection.execute(f'UPDATE {USERS_TABLE} SET Work_hours_total={working_hours} where id={user_id}')


def try_to_add_user_to_office(connection, user_id, working_hours):
    """Попытаться добавить пользователя на свободное место в офисе или добавить его в очередь"""
    offices = get_users_in_offices(connection)
    offices = [{'Office': x, 'Users': len(offices[x]) if not offices[x] is None else 0} for x in offices.keys()]
    min_load = min([x['Users'] for x in offices])
    if min_load < OFFICE_CAPACITY:
        office_id = [x['Office'] for x in offices if x['Users'] == min_load][0]
        add_user_to_office(connection, office_id, user_id)
    else:
        add_user_to_queue(connection, user_id)
    set_working_hours(connection, user_id, working_hours)


def get_users_in_queue(connection):
    """Получить список пользователей в очереди в порядке добавления"""
    result = connection.execute(f'select us.First_Name, us.Last_name, US.ID from {QUEUE_TABLE} Qu '
                                f'LEFT JOIN {USERS_TABLE} US on US.ID=Qu.User_ID '
                                f'ORDER BY Qu.ID')
    data = result.fetchall()
    return [{'First_name': x[0], 'Last_name': x[1], 'user_id': x[2]} for x in data]


def get_users_info(connection):
    """Получить список пользователей с полной информацией"""
    result = connection.execute(f'select First_Name, Last_name, Remaining_work_time, '
                                f'Of.Number, Qu.User_ID, Us.ID, Work_hours_total '
                                f'from {USERS_TABLE} Us '
                                f'left join offices Of on Us.office_id=Of.ID '
                                f'left join queue Qu on Us.ID=Qu.User_ID '
                                f'ORDER BY Us.ID')
    data = result.fetchall()
    return [{'First_name': x[0], 'Last_name': x[1], 'R_work_time': x[2], 'Office': x[3],
             'Queue': x[4], 'user_id': x[5], "Work_hours": x[6]} for x in data]


def process_time_step(connection, time_step):
    """Смещение текущего времени на указанное количество часов"""
    users = get_users_info(connection)
    queue = get_users_in_queue(connection)
    users = [x for x in users if not x['Office'] is None]
    for user in users:
        if user['R_work_time'] <= time_step:
            user['Work_hours'] += user['R_work_time']
            set_total_working_hours(connection, user['user_id'], user['Work_hours'])
            remove_user_from_office(connection, user['user_id'])
            set_working_hours(connection, user['user_id'], 0)
            if len(queue) > 0:
                moved_user = queue.pop(0)
                remove_user_from_queue(connection, moved_user['user_id'])
                user_working_hours = get_user_working_hours(connection, moved_user['user_id'])
                try_to_add_user_to_office(connection, moved_user['user_id'], user_working_hours)
        else:
            user['Work_hours'] += time_step
            set_total_working_hours(connection, user['user_id'], user['Work_hours'])
            set_working_hours(connection, user['user_id'], user['R_work_time'] - time_step)
