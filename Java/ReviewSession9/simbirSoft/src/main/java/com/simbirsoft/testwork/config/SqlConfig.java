package com.simbirsoft.testwork.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class SqlConfig {
    public static final String DB_URL = "db.url";
    public static final String DB_USER = "db.user";
    public static final String DB_PASSWORD = "db.password";

    private static final Properties properties = new Properties();
    private static final Logger logger = LogManager.getLogger(SqlConfig.class.getName());

    public static String getProperties(String name) {
        if (properties.isEmpty()) {
            try (InputStream is = SqlConfig.class.getClassLoader().getResourceAsStream("sql.properties")) {
                properties.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e);
                throw new RuntimeException(e);
            }
        }
        return properties.getProperty(name);
    }

}
