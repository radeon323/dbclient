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

    public static Table mapRow(ResultSet resultSet) throws SQLException {
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

        //set lines
        List<List<Object>> lines = new ArrayList<>();
        while (resultSet.next()) {
            List<Object> line = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                line.add(resultSet.getObject(i));
            }
            lines.add(line);
        }
        table.setValues(lines);

        return table;
    }
}
