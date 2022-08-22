package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.Table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
public class DataMapper {

    public Table mapRow(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        Table table = new Table();

        //set table name
        String tableName = metaData.getTableName(1);
        table.setName(tableName);

        //set headers
        List<String> headers = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            headers.add(metaData.getColumnName(i));
        }
        table.setHeaders(headers);

        //set rows
        List<List<Object>> rows = new ArrayList<>();
        while (resultSet.next()) {
            List<Object> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getObject(i));
            }
            rows.add(row);
        }
        table.setValues(rows);

        return table;
    }
}
