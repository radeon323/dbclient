package com.olshevchenko.dbclient.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Oleksandr Shevchenko
 */
public class ConnectionManager {
    private static final Properties properties = PropertiesReader.getProperties();
    private static final String jdbcURL = properties.getProperty("jdbc_url");
    private static final String jdbcUser = properties.getProperty("jdbc_user");
    private static final String jdbcPass = properties.getProperty("jdbc_password");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
    }
}
