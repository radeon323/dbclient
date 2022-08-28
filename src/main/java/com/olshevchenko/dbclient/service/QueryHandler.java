package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.QueryResult;
import com.olshevchenko.dbclient.entity.SqlOperator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @author Oleksandr Shevchenko
 */
@Slf4j
@Getter
@AllArgsConstructor
public class QueryHandler {
    private final DataSource dataSource;

    public QueryResult handle(String query) {
        SqlOperator operator = SqlOperator.getOperatorByName(query);
        if (operator.equals(SqlOperator.SELECT)) {
            return handleResultSet(query, operator);
        } else {
            return handleAction(query, operator);
        }
    }

    QueryResult handleResultSet(String query, SqlOperator operator) {
        try (Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query)) {

            QueryResultMapper queryResultMapper = new QueryResultMapper();
            QueryResult queryResult = queryResultMapper.extractQueryResult(rs);
            queryResult.setOperator(operator);
            return queryResult;
        } catch (SQLException e) {
            log.error("Cannot execute query: {} ", query, e);
            throw new RuntimeException("Cannot execute query: {} " + query, e);
        }
    }

    QueryResult handleAction(String query, SqlOperator operator) {
        try (Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {

            int rows = statement.executeUpdate(query);
            QueryResult queryResult = new QueryResult();
            queryResult.setRowsUpdated(rows);
            queryResult.setOperator(operator);
            return queryResult;
        } catch (SQLException e) {
            log.error("Cannot execute query: {} ", query, e);
            throw new RuntimeException("Cannot execute query: {} " + query, e);
        }
    }


}
