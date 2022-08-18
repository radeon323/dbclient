package com.olshevchenko.dbclient.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Oleksandr Shevchenko
 */
public class ConnectionManager {
    static String jdbcURL = "jdbc:postgresql://localhost:5432/testDB";
    static String jdbcUser = "postgres";
    static String jdbcPass = "postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
    }
}
