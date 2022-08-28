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
    private static final QueryResult queryResult = new QueryResult();
    private static final String pathToResultDir = new PropertiesReader().getProperties("application.properties").getProperty("report.html.path");
    private static final File resultDir = new File(pathToResultDir);

    @BeforeEach
    public void before() {
        queryResult.setTableName("persons");
        queryResult.setHeaders(List.of("id","name","age"));
        queryResult.setValues(List.of(List.of("1","Sasha","40"),List.of("2","Tolik","32")));
    }

    @AfterAll
    static void afterAll() {
        File htmlTable = new File(resultDir, "table_" + queryResult.getTableName() + ".html");
        htmlTable.delete();
    }

    @Test
    void testWriteTable() {
        QueryResultHtmlWriter queryResultHtmlWriter = new QueryResultHtmlWriter(queryResult,pathToResultDir);
        File htmlTable = new File(resultDir, "table_" + queryResult.getTableName() + ".html");
        queryResultHtmlWriter.writeTable();
        assertTrue(htmlTable.exists());
    }
}