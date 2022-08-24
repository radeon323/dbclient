package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.QueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Oleksandr Shevchenko
 */
@ExtendWith(MockitoExtension.class)
class QueryResultConsoleWriterTest {
    private final QueryResult queryResult = new QueryResult();
    private final StringBuilder sb = new StringBuilder();

    @BeforeEach
    public void before() {
        queryResult.setTableName("persons");
        queryResult.setHeaders(List.of("id","name","age"));
        queryResult.setValues(List.of(List.of("1","Sasha","40"),List.of("2","Tolik","32")));

        sb.append("1").append("2");
    }


    @Test
    void testDetectMaxWidthOfElement() {
        QueryResultConsoleWriter queryResultConsoleWriter = new QueryResultConsoleWriter(queryResult);
        int actual = queryResultConsoleWriter.detectMaxWidthOfElement(queryResult.getHeaders(), queryResult.getValues(),1);
        assertEquals(5, actual);
    }

    @Test
    void testReplaceLastSymbol() {
        QueryResultConsoleWriter queryResultConsoleWriter = new QueryResultConsoleWriter(queryResult);
        queryResultConsoleWriter.replaceLastSymbol(sb,1, "3");
        assertEquals("13", sb.toString());
    }

}