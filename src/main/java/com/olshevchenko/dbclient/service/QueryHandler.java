package com.olshevchenko.dbclient.service;


import com.olshevchenko.dbclient.entity.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.*;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@RequiredArgsConstructor
public class QueryHandler {
    private static final String SELECT = "SELECT";
    private final Statement statement;
    private final String query;

    public void handle() throws SQLException {
        String operator = query.split(" ")[0].toUpperCase();
        String tableName = detectTableNameFromQuery(operator);
        if (!isTableExists(tableName)) {
            System.out.println("Table with name '" + tableName + "' does not exist! Enter correct table name");
        } else {
            System.out.println("Query " + operator + "... was successfully executed.");
            if (operator.equals(SELECT)) {
                handleResultSet();
            } else {
                handleAction(operator);
            }
        }
    }

    private void handleResultSet() {
        try {
            ResultSet rs = statement.executeQuery(query);
            Table table = DataMapper.mapRow(rs);
            QueryResultConsoleWriter.writeTable(table);
            QueryResultHtmlWriter.writeTable(table);
        } catch (SQLException e) {
            throw new RuntimeException(query, e);
        }
    }

    private void handleAction(String operator) {
        try {
            int rows = statement.executeUpdate(query);
            QueryResultConsoleWriter.writeAction(operator, rows);
        } catch (SQLException e) {
            throw new RuntimeException(query, e);
        }
    }

    private String detectTableNameFromQuery(String operator) {
        String tableName = null;
        String[] s = query.split(" ");
        for (int i = 0; i < s.length; i++) {
            if (s[i].equalsIgnoreCase("FROM")) {
                tableName = s[i+1];
            }
            if (!operator.equalsIgnoreCase("CREATE") && s[i].equalsIgnoreCase("TABLE")) {
                tableName = s[i+1];
            }
            if (s[i].equalsIgnoreCase("INTO")) {
                tableName = s[i+1];
            }
            if (s[i].equalsIgnoreCase("UPDATE")) {
                tableName = s[i+1];
            }
        }
        return tableName;
    }

    private boolean isTableExists(String tableName) {
        String query = "SELECT count(*) FROM information_schema.tables WHERE table_name = '" + tableName + "' LIMIT 1;";
        try {
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            return rs.getInt(1) != 0;
        } catch (SQLException e) {
            throw new RuntimeException(query, e);
        }
    }



}
