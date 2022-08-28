package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.QueryResult;
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
class QueryResultMapperTest {

    @Test
    void testMapRow() throws SQLException {
        QueryResultMapper queryResultMapper = new QueryResultMapper();

        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        ResultSet rs = mock(ResultSet.class);
        when(rs.getMetaData()).thenReturn(metaData);

        when(metaData.getColumnCount()).thenReturn(3);
        when(metaData.getTableName(1)).thenReturn("persons");
        when(metaData.getColumnName(1)).thenReturn("id");
        when(metaData.getColumnName(2)).thenReturn("name");
        when(metaData.getColumnName(3)).thenReturn("age");

        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getObject(1)).thenReturn(1);
        when(rs.getObject(2)).thenReturn("Sasha");
        when(rs.getObject(3)).thenReturn(40);

        QueryResult queryResult = queryResultMapper.extractQueryResult(rs);

        assertEquals("persons", queryResult.getTableName());
        assertEquals(List.of ("id","name","age"), queryResult.getHeaders());
        assertEquals(List.of (List.of (1,"Sasha",40)), queryResult.getValues());
    }


    @Test
    void testGetTableName() throws SQLException {
        QueryResultMapper queryResultMapper = new QueryResultMapper();
        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        when(metaData.getTableName(1)).thenReturn("persons");

        String tableName = queryResultMapper.getTableName(metaData);
        assertEquals("persons", tableName);
    }

    @Test
    void testGetHeaders() throws SQLException {
        QueryResultMapper queryResultMapper = new QueryResultMapper();
        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        when(metaData.getColumnName(1)).thenReturn("id");
        when(metaData.getColumnName(2)).thenReturn("name");
        when(metaData.getColumnName(3)).thenReturn("age");

        List<String> headers = queryResultMapper.getHeaders(metaData,3);
        assertEquals(List.of ("id","name","age"), headers);
    }

    @Test
    void testGetRows() throws SQLException {
        QueryResultMapper queryResultMapper = new QueryResultMapper();
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getObject(1)).thenReturn(1);
        when(rs.getObject(2)).thenReturn("Sasha");
        when(rs.getObject(3)).thenReturn(40);

        List<List<Object>> rows = queryResultMapper.getRows(rs,3);
        assertEquals(List.of (List.of (1,"Sasha",40)), rows);
    }


}

