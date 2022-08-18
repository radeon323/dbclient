package com.olshevchenko.dbclient.Service;

import com.olshevchenko.dbclient.entity.Table;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Oleksandr Shevchenko
 */
@ExtendWith(MockitoExtension.class)
class DataMapperTest {

//TODO ???
    @Test
    void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        ResultSetMetaData metaData = mock(ResultSetMetaData.class);

        when(metaData.getColumnCount()).thenReturn(3);

        when(metaData.getTableName(1)).thenReturn("persons");

        when(metaData.getColumnName(1)).thenReturn("id");
        when(metaData.getColumnName(2)).thenReturn("name");
        when(metaData.getColumnName(3)).thenReturn("age");

        when(rs.getObject("id")).thenReturn(List.of (1));
        when(rs.getObject("name")).thenReturn(List.of ("Sasha"));
        when(rs.getObject("age")).thenReturn(List.of (40));

        Table table = DataMapper.mapRow(rs);

        System.out.println(table);
    }


}

