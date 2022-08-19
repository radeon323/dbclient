package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.Table;

import java.util.*;

/**
 * @author Oleksandr Shevchenko
 */
public class QueryResultWriter {

    public static void writeTable(Table table) {
        System.out.println("Table '" + table.getName() + "':");
        List<String> headers = table.getHeaders();
        List<List<Object>> values = table.getValues();
        
        StringBuilder headerLine = new StringBuilder("│ ");
        StringBuilder upperBorder = new StringBuilder("┌");
        StringBuilder lineSeparator = new StringBuilder("├");
        List<StringBuilder> lines = new ArrayList<>();
        StringBuilder bottomBorder = new StringBuilder("└");


        int tableWidth = headers.size() * 3;
        int columnCount = 0;
        List<Integer> width = new ArrayList<>();

        //append line separators and headers
        for (String header : headers) {
            int headerWidth = detectMaxWidthOfElement(headers, values, columnCount);
            width.add(headerWidth);
            tableWidth += headerWidth;
            String repeat = "─".repeat(headerWidth + 2);
            upperBorder.append(repeat).append("┬");
            extendWidthOfCell(headerLine, header, headerWidth);
            lineSeparator.append(repeat).append("┼");
            bottomBorder.append(repeat).append("┴");
            columnCount++;
        }

        //append values for cells
        for (List<Object> value : values) {
            StringBuilder valuesLine = new StringBuilder("│ ");
            int i = 0;
            for (Object o : value) {
                extendWidthOfCell(valuesLine, o.toString(), width.get(i++));
            }
            replaceLastSymbol(valuesLine, tableWidth, "│");
            lines.add(valuesLine);
        }

        //replace last symbol in table
        replaceLastSymbol(upperBorder, tableWidth, "┐");
        replaceLastSymbol(headerLine, tableWidth, "│");
        replaceLastSymbol(lineSeparator, tableWidth, "┤");
        replaceLastSymbol(bottomBorder, tableWidth, "┘");

        //print view
        System.out.println(upperBorder);
        System.out.println(headerLine);
        for (int i = 0; i < values.size(); i++) {
            System.out.println(lineSeparator);
            System.out.println(lines.get(i));
        }
        System.out.println(bottomBorder);

    }

    public static void writeAction(String operator) {
        System.out.println("Command " + operator + " was successfully executed.");
    }

    private static void extendWidthOfCell(StringBuilder sb, String element, int headerLength) {
        if (element.length() >= headerLength) {
            sb.append(element).append(" │ ");
        } else {
            int i = headerLength - element.length();
            String repeat = " ".repeat(i);
            sb.append(element).append(repeat).append(" │ ");
        }
    }

    private static void replaceLastSymbol(StringBuilder upperBorder, int tableWidth, String symbol) {
        upperBorder.replace(tableWidth, tableWidth + 1, symbol);
    }

    private static int detectMaxWidthOfElement(List<String> headers, List<List<Object>> values, int columnNumber) {
        List<String> col = new ArrayList<>();
        col.add(headers.get(columnNumber));
        for (int i = 0; i < values.size(); i++) {
            col.add(values.get(i).get(columnNumber).toString());
        }
        return col.stream()
                .map(String::length)
                .max(Comparator.naturalOrder())
                .get();
    }


}
