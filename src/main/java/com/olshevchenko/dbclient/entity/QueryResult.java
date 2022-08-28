package com.olshevchenko.dbclient.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class QueryResult {
    private String tableName;
    private List<String> headers;
    private List<List<Object>> values;
    private int rowsUpdated;
    private SqlOperator operator;
}
