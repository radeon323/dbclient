package com.olshevchenko.dbclient.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.Statement;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Oleksandr Shevchenko
 */
@ExtendWith(MockitoExtension.class)
class QueryHandlerTest {
    private static String SELECT = "SELECT";
    private Statement statement;
    private String query;

    @BeforeEach
    public void before() throws Exception {
        Statement statement = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);
        when(statement.executeQuery(anyString())).thenReturn(rs);
        when(statement.executeUpdate(anyString())).thenReturn(1);
    }

    //TODO:  x3
    @Test
    void handle() {

    }


}