package com.olshevchenko.dbclient.entity;

import java.util.Arrays;

/**
 * @author Oleksandr Shevchenko
 */
public enum SqlOperator {
    SELECT,
    CREATE,
    DROP,
    ALTER,
    BACKUP,
    INSERT,
    UPDATE,
    DELETE;

    public static SqlOperator getOperatorByName(String query) {
        return Arrays.stream(values())
                .filter(statement -> query.toUpperCase().startsWith(statement.name()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid statement: " + query.toUpperCase()));
    }


}
