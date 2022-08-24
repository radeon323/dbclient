package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.QueryResult;
import com.olshevchenko.dbclient.utils.PropertiesReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oleksandr Shevchenko
 */
@ExtendWith(MockitoExtension.class)
class QueryResultHtmlWriterTest {
    private static final QueryResult QUERY_RESULT = new QueryResult();
    private static final String RESULT_DIR = PropertiesReader.getProperties("application.properties").getProperty("report.html");
    private static final File RESOURCES_DIR = new File(RESULT_DIR);

    @BeforeEach
    public void before() {
        QUERY_RESULT.setTableName("persons");
        QUERY_RESULT.setHeaders(List.of("id","name","age"));
        QUERY_RESULT.setValues(List.of(List.of("1","Sasha","40"),List.of("2","Tolik","32")));
    }

    @AfterAll
    static void afterAll() {
        File htmlTable = new File(RESOURCES_DIR, "table_" + QUERY_RESULT.getTableName() + ".html");
        htmlTable.delete();
    }

    @Test
    void testWriteTable() {
        QueryResultConsoleWriter queryResultConsoleWriter = new QueryResultConsoleWriter(QUERY_RESULT);
        File htmlTable = new File(RESOURCES_DIR, "table_" + QUERY_RESULT.getTableName() + ".html");
        queryResultConsoleWriter.writeTable();
        assertTrue(htmlTable.exists());
    }
}