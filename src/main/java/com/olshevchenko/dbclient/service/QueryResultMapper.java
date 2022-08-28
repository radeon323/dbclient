package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.QueryResult;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
public class QueryResultMapper {

    public QueryResult extractQueryResult(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        QueryResult queryResult = new QueryResult();
        queryResult.setTableName(getTableName(metaData));
        queryResult.setHeaders(getHeaders(metaData, columnCount));
        queryResult.setValues(getRows(resultSet, columnCount));
        return queryResult;
    }

    String getTableName(ResultSetMetaData metaData) throws SQLException {
        return metaData.getTableName(1);
    }

    List<String> getHeaders(ResultSetMetaData metaData, int columnCount) throws SQLException {
        List<String> headers = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            headers.add(metaData.getColumnName(i));
        }
        return headers;
    }

    List<List<Object>> getRows(ResultSet resultSet, int columnCount) throws SQLException {
        List<List<Object>> rows = new ArrayList<>();
        while (resultSet.next()) {
            List<Object> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getObject(i));
            }
            rows.add(row);
        }
        return rows;
    }


}
