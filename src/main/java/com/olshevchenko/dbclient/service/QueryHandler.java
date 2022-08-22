package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@RequiredArgsConstructor
public class QueryHandler {
    private static final String SELECT = "SELECT";
    private static final DataMapper dataMapper = new DataMapper();
    private final DataSource dataSource;
    private final String query;

    public void handle() {
        String operator = query.split(" ")[0].toUpperCase();
        String tableName = detectTableNameFromQuery(operator);

        if (tableName.equalsIgnoreCase("INFORMATION_SCHEMA.TABLES")) {
            List<String> tableNames = getAllTableNames();
            for (String name : tableNames) {
                handleResultSet("SELECT * FROM " + name);
            }
        } else if (!isTableExists(tableName) && !operator.equalsIgnoreCase("CREATE")) {
            System.out.println("Table with name '" + tableName + "' does not exist! Enter correct table name");
        } else {
            System.out.println("Query " + operator + "... was successfully executed.");
            if (operator.equals(SELECT)) {
                handleResultSet(query);
            } else {
                handleAction(operator);
            }
        }
    }

    protected void handleResultSet(String query) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery();) {

            Table table = dataMapper.mapRow(rs);
            QueryResultConsoleWriter.writeTable(table);
            QueryResultHtmlWriter.writeTable(table);
        } catch (SQLException e) {
            throw new RuntimeException(query, e);
        }
    }

    private void handleAction(String operator) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();) {

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
            if (operator.equalsIgnoreCase("CREATE") && s[i].equalsIgnoreCase("TABLE")) {
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery();) {

            rs.next();
            return rs.getInt(1) != 0;
        } catch (SQLException e) {
            throw new RuntimeException(query, e);
        }
    }

    private List<String> getAllTableNames() {
        String query = "SELECT table_name FROM information_schema.tables where table_schema = 'public'";
        List<String> tableNames = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery();) {

            while (rs.next()) {
                tableNames.add(rs.getString(1));
            }
            return tableNames;
        } catch (SQLException e) {
            throw new RuntimeException(query, e);
        }
    }



}
