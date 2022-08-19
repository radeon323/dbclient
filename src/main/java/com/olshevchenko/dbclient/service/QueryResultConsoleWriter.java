package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.Table;

import java.util.*;

/**
 * @author Oleksandr Shevchenko
 */
public class QueryResultConsoleWriter {

    public static void writeTable(Table table) {
        System.out.println("Table '" + table.getName() + "':");
        List<String> headers = table.getHeaders();
        List<List<Object>> values = table.getValues();

        StringBuilder upperBorder = new StringBuilder("┌");
        StringBuilder row = new StringBuilder("│ ");
        StringBuilder rowsSeparator = new StringBuilder("├");
        StringBuilder bottomBorder = new StringBuilder("└");

        int tableWidth = headers.size() * 3;
        int columnCount = 0;

        //append line separators and headers
        List<Integer> columnWidthsList = new ArrayList<>();
        for (String header : headers) {
            int columnWidth = detectMaxWidthOfElement(headers, values, columnCount);
            columnWidthsList.add(columnWidth);
            tableWidth += columnWidth;
            String solidLine = "─".repeat(columnWidth + 2);
            upperBorder.append(solidLine).append("┬");
            extendWidthOfCellAndAppendValue(row, header, columnWidth);
            rowsSeparator.append(solidLine).append("┼");
            bottomBorder.append(solidLine).append("┴");
            columnCount++;
        }

        //append values for cells
        List<StringBuilder> lines = new ArrayList<>();
        for (List<Object> valueList : values) {
            StringBuilder valuesLine = new StringBuilder("│ ");
            int i = 0;
            for (Object value : valueList) {
                extendWidthOfCellAndAppendValue(valuesLine, value.toString(), columnWidthsList.get(i++));
            }
            replaceLastSymbol(valuesLine, tableWidth, "│");
            lines.add(valuesLine);
        }

        //replace last symbol in table
        replaceLastSymbol(upperBorder, tableWidth, "┐");
        replaceLastSymbol(row, tableWidth, "│");
        replaceLastSymbol(rowsSeparator, tableWidth, "┤");
        replaceLastSymbol(bottomBorder, tableWidth, "┘");

        //print view
        System.out.println(upperBorder);
        System.out.println(row);
        for (int i = 0; i < values.size(); i++) {
            System.out.println(rowsSeparator);
            System.out.println(lines.get(i));
        }
        System.out.println(bottomBorder);

    }

    public static void writeAction(String operator, int rows) {
        String row = "row";
        String was = "was";
        String ed = "ed.";
        if (rows > 1) {
            row = "rows";
            was = "were";
        }
        if (operator.toLowerCase().endsWith("e")) {
            ed = "d.";
        }
        System.out.println("Command " + operator + " was successfully executed. " + rows + " " + row + " " + was + " " + operator.toLowerCase() + ed);
    }

    private static void extendWidthOfCellAndAppendValue(StringBuilder sb, String value, int columnWidth) {
        if (value.length() >= columnWidth) {
            sb.append(value).append(" │ ");
        } else {
            int i = columnWidth - value.length();
            String gapLeft = " ".repeat(i/2);;
            String gapRight;
            if (i%2 != 0) {
                gapRight = " ".repeat(i/2 + 1);
            } else {
                gapRight = gapLeft;
            }
            sb.append(gapLeft).append(value).append(gapRight).append(" │ ");
        }
    }

    private static void replaceLastSymbol(StringBuilder sb, int tableWidth, String symbol) {
        sb.replace(tableWidth, tableWidth + 1, symbol);
    }

    private static int detectMaxWidthOfElement(List<String> headers, List<List<Object>> values, int columnNumber) {
        List<String> columnValues = new ArrayList<>();
        columnValues.add(headers.get(columnNumber));
        for (List<Object> value : values) {
            columnValues.add(value.get(columnNumber).toString());
        }
        return columnValues.stream()
                .map(String::length)
                .max(Comparator.naturalOrder())
                .get();
    }


}
