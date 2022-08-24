package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.QueryResult;
import com.olshevchenko.dbclient.entity.SqlOperator;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
@Slf4j
public class QueryResultHtmlWriter {
    private final QueryResult queryResult;
    private final File RESOURCES_DIR;

    public QueryResultHtmlWriter(QueryResult queryResult, String RESULT_DIR) {
        this.queryResult = queryResult;
        this.RESOURCES_DIR = new File(RESULT_DIR);
    }

    private static final String ADD_TAGS_START =
                                                """
                                                    <!DOCTYPE html>
                                                    <html>
                                                    <head>
                                                    <style>
                                                    body {font-family: Arial, Helvetica, sans-serif;}
                                                    h2 {text-align: center;}
                                                    table {margin: auto; text-align: center; border-collapse: collapse;}
                                                    td, th {padding:7px; padding-left:20px;padding-right:20px;border: 1px solid;}
                                                    </style>
                                                    </head>
                                                    <body>
                                                    """;
    private static final String ADD_TAGS_FINISH =
                                                """
                                                    </body>
                                                    </html>
                                                    """;

    public void writeResult() {
        if (queryResult.getOperator().equals(SqlOperator.SELECT)) {
            writeTable();
        }
    }

    void writeTable() {
        if (!RESOURCES_DIR.isDirectory()) {
            RESOURCES_DIR.mkdir();
        }
        File htmlTable = new File(RESOURCES_DIR, "table_" + queryResult.getTableName() + ".html");
        List<String> headers = queryResult.getHeaders();
        List<List<Object>> values = queryResult.getValues();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(htmlTable))) {
            bufferedWriter.write(ADD_TAGS_START);
            bufferedWriter.write("<h2>Table '" + queryResult.getTableName() + "':</h2>");

            bufferedWriter.write("<table>");

            bufferedWriter.write("<tr>");
            for (String header : headers) {
                bufferedWriter.write("<th>" + header + "</th>");
            }
            bufferedWriter.write("</tr>");

            for (List<Object> valueList : values) {
                bufferedWriter.write("<tr>");
                for (Object value : valueList) {
                    bufferedWriter.write("<td>" + value + "</td>");
                }
                bufferedWriter.write("</tr>");
            }

            bufferedWriter.write("</table>");
            bufferedWriter.write(ADD_TAGS_FINISH);

        } catch (IOException e) {
            log.error("Cannot create file: {} ", htmlTable, e);
            throw new RuntimeException("Cannot create file: {} " + htmlTable, e);
        }


    }


}
