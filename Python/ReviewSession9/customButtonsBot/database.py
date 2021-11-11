#-*- coding: utf-8 -*-

import sqlite3


class DatabaseManager:
    def __init__(self):
        pass

    def get_message_with_id(self, id):
        """
        Получение сообщения по его id
        :param message_id
        :return str
        """
        connect = sqlite3.connect("data.db")
        with connect:
            cursor = connect.cursor()
            result = cursor.execute("""
                    SELECT text
                    FROM messages
                    WHERE id = ?
                """, (id, )).fetchone()

        return result[0]

    def get_buttons(self):
        """
        Получение всех кнопок
        :param none
        :return cortege
        """
        connect = sqlite3.connect("data.db")
        with connect:
            cursor = connect.cursor()
            result = cursor.execute("""
                    SELECT *
                    FROM markup
                """).fetchall()

        return result

    def get_admins(self):
        """
        Получение идентификаторов всех аккаунтов
        у который есть доступы к админ панели
        :param none
        :return cortege
        """
        connect = sqlite3.connect("data.db")
        with connect:
            cursor = connect.cursor()
            result = cursor.execute("""
                    SELECT user_id
                    FROM admins
                """).fetchall()

        return result[0]

    def add_admin(self, user_id):
        """
        Добавляет нового пользователя в таблицу админов
        :param user_id
        :return none
        """
        connect = sqlite3.connect("data.db")
        with connect:
            cursor = connect.cursor()
            cursor.execute("""
                    INSERT INTO admins
                    (user_id)
                    VALUES 
                    ( ? )
                """, (user_id, ))

            connect.commit()

    def change_button_text_with_id(self, id, text):
        """
        Изменяет текст на кнопке
        :param button_id
        :param new_text
        :return none
        """        
        connect = sqlite3.connect("data.db")
        with connect:
            cursor = connect.cursor()
            cursor.execute("""
                   UPDATE markup
                   SET text = ?
                   WHERE id = ?
                """, (text, id, ))

            connect.commit()

    def change_button_message_with_id(self, id, text):
        """
        Изменяет текст сообщения, который присылает кнопка
        :param button_id
        :param new_text
        :return none
        """        
        connect = sqlite3.connect("data.db")
        with connect:
            cursor = connect.cursor()
            cursor.execute("""
                   UPDATE markup
                   SET message = ?
                   WHERE id = ?
                """, (text, id, ))

            connect.commit() 

    def remove_button(self, id):
        """
        Удаляет кнопку
        :param button_id
        :return none
        """             
        connect = sqlite3.connect("data.db")
        with connect:
            cursor = connect.cursor()
            cursor.execute("""
                    DELETE
                    FROM markup
                    WHERE id = ?
                """, (id, ))

            connect.commit() 

    def create_button(self, text, msg, row):
        """
        Добавляет кнопку
        :param text
        :param message
        :param row
        :return none
        """    
        connect = sqlite3.connect("data.db")
        with connect:
            cursor = connect.cursor()
            cursor.execute("""
                    INSERT INTO markup
                    ( row, text, message )
                    VALUES
                    ( ?, ?, ? )
                """, (row, text, msg, ))

            connect.commit()         

    def get_button_with_text(self, text):
        """
        Получаем данные кнопки по ее тексту
        :param text
        :return cortege
        """
        connect = sqlite3.connect("data.db")
        with connect:
            cursor = connect.cursor()
            result = cursor.execute("""
                    SELECT *
                    FROM markup
                    WHERE text = ?
                """, (text, )).fetchone()

        return result