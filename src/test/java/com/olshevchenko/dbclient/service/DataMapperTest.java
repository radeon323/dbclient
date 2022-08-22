package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.Table;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Oleksandr Shevchenko
 */
class DataMapperTest {

    @Test
    void testMapRow() throws SQLException {
        DataMapper dataMapper = new DataMapper();

        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        ResultSet rs = mock(ResultSet.class);
        when(rs.getMetaData()).thenReturn(metaData);

        when(metaData.getColumnCount()).thenReturn(3);
        when(metaData.getTableName(1)).thenReturn("persons");
        when(metaData.getColumnName(1)).thenReturn("id");
        when(metaData.getColumnName(2)).thenReturn("name");
        when(metaData.getColumnName(3)).thenReturn("age");

        //TODO does not set values!!
        when(rs.getObject("id")).thenReturn(List.of (1));
        when(rs.getObject("name")).thenReturn(List.of ("Sasha"));
        when(rs.getObject("age")).thenReturn(List.of (40));

        Table table = dataMapper.mapRow(rs);

        System.out.println(table);

        assertEquals("persons", table.getName());
        assertEquals(List.of ("id","name","age"), table.getHeaders());

        assertEquals(List.of (List.of (1),List.of ("Sasha"),List.of (40)), table.getValues());
    }


    @Test
    void testGetTableName() throws SQLException {
        DataMapper dataMapper = new DataMapper();
        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        when(metaData.getTableName(1)).thenReturn("persons");

        String tableName = dataMapper.getTableName(metaData);
        assertEquals("persons", tableName);
    }

    @Test
    void testGetHeaders() throws SQLException {
        DataMapper dataMapper = new DataMapper();
        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        when(metaData.getColumnName(1)).thenReturn("id");
        when(metaData.getColumnName(2)).thenReturn("name");
        when(metaData.getColumnName(3)).thenReturn("age");

        List<String> headers = dataMapper.getHeaders(metaData,3);
        assertEquals(List.of ("id","name","age"), headers);
    }

    //TODO does not set values!!
    @Test
    void testGetRows() throws SQLException {
        DataMapper dataMapper = new DataMapper();
        ResultSet rs = mock(ResultSet.class);
        when(rs.getObject("id")).thenReturn(List.of (1));
        when(rs.getObject("name")).thenReturn(List.of ("Sasha"));
        when(rs.getObject("age")).thenReturn(List.of (40));

        List<List<Object>> rows = dataMapper.getRows(rs,3);
        assertEquals(List.of (List.of (1),List.of ("Sasha"),List.of (40)), rows);
    }


}

