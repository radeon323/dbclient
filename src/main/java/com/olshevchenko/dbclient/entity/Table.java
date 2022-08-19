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
public class Table {
    private String name;
    private List<String> headers;
    private List<List<Object>> values;
}
