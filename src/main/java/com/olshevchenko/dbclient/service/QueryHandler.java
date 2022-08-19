package com.olshevchenko.dbclient.service;


import com.olshevchenko.dbclient.entity.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        System.out.println("Query " + operator + "... was successfully executed.");
        if (operator.equals(SELECT)) {
            handleResultSet();
        } else {
            handleAction(operator);
        }
    }

    private void handleResultSet() {
        try {
            ResultSet rs = statement.executeQuery(query);
            Table table = DataMapper.mapRow(rs);
            QueryResultWriter.writeTable(table);
        } catch (SQLException e) {
            throw new RuntimeException(query, e);
        }
    }

    private void handleAction(String operator) {
        try {
            statement.executeUpdate(query);
            QueryResultWriter.writeAction(operator);
        } catch (SQLException e) {
            throw new RuntimeException(query, e);
        }
    }


}
