package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Oleksandr Shevchenko
 */
@ExtendWith(MockitoExtension.class)
class QueryResultConsoleWriterTest {
    private final Table table = new Table();

    @BeforeEach
    public void before() throws Exception {
        table.setName("persons");
        table.setHeaders(List.of("id","name","age"));
        table.setValues(List.of(List.of("1","Sasha","40"),List.of("2","Tolik","32")));
    }

    @Test
    void writeTable() {
        QueryResultConsoleWriter.writeTable(table);
    }

    @Test
    void writeAction() {
    }
}