package com.simbirsoft.testwork.util;

import com.simbirsoft.testwork.exeption.StorageException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class ExceptionConvert {

    private static final Logger logger = LogManager.getLogger(ExceptionConvert.class.getName());

    public static StorageException convert(SQLException e) {
        logger.info(e.getSQLState());
        if (e.getSQLState().equals("23505")) {
            return new StorageException("Exist DataBase");
        } else if (e.getSQLState().equals("22023")) {
            return new StorageException("Not exist DataBase");
        }
        return new StorageException(e.getMessage());
    }

}
