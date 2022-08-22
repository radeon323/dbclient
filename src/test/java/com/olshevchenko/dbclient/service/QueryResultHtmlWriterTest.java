package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.Table;
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
    private static final Table table = new Table();
    private static final String RESULT_DIR = PropertiesReader.getProperties().getProperty("result_dir");
    private static final File RESOURCES_DIR = new File(RESULT_DIR);

    @BeforeEach
    public void before() {
        table.setName("persons");
        table.setHeaders(List.of("id","name","age"));
        table.setValues(List.of(List.of("1","Sasha","40"),List.of("2","Tolik","32")));
    }

    @AfterAll
    static void afterAll() {
        File htmlTable = new File(RESOURCES_DIR, "table_" + table.getName() + ".html");
        htmlTable.delete();
    }

    @Test
    void testWriteTable() {
        File htmlTable = new File(RESOURCES_DIR, "table_" + table.getName() + ".html");
        QueryResultHtmlWriter.writeTable(table);
        assertTrue(htmlTable.exists());
    }
}