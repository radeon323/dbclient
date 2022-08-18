package com.olshevchenko.dbclient.Service;

import com.olshevchenko.dbclient.entity.Table;

/**
 * @author Oleksandr Shevchenko
 */
public class QueryResultWriter {

    public static void writeTable(Table table) {
        System.out.println(table);
    }

    public static void writeAction(String operator) {
        System.out.println("Command " + operator + " was successfully executed.");
    }


}
