package com.olshevchenko.dbclient.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@Setter
@ToString
public class QueryResult {
    private String tableName;
    private List<String> headers;
    private List<List<Object>> values;
    private int rowsUpdated = 0;
    private SqlOperator operator;
}
