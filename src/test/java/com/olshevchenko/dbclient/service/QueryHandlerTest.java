package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.Table;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.*;

/**
 * @author Oleksandr Shevchenko
 */
@ExtendWith(MockitoExtension.class)
class QueryHandlerTest {
    private final String query = "SELECT * FROM persons";

    @Mock
    private Statement statement;

    @Mock
    private ResultSet rs;


    //TODO:  x3
    @Test
    void handle() throws SQLException {

        ResultSetMetaData rsMetaData = mock(ResultSetMetaData.class);

        when(statement.executeQuery(query)).thenReturn(rs);
        when(rs.getMetaData()).thenReturn(mock(ResultSetMetaData.class));

        QueryHandler queryHandler = new QueryHandler(statement, query);
        Table table = new Table();
        queryHandler.handle();
    }


}