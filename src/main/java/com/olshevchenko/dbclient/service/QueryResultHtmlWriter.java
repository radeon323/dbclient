package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.Table;
import com.olshevchenko.dbclient.utils.PropertiesReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
public class QueryResultHtmlWriter {
    private static final String RESULT_DIR = PropertiesReader.getProperties().getProperty("result_dir");
    private static final File RESOURCES_DIR = new File(RESULT_DIR);
    private static final String ADD_TAGS_START =
                                                """
                                                    <!DOCTYPE html>
                                                    <html>
                                                    <head>
                                                    \t <style>
                                                    \t\t body {font-family: Arial, Helvetica, sans-serif;}
                                                    \t\t h2 {text-align: center;}
                                                    \t\t table {margin: auto; text-align: center; border-collapse: collapse;}
                                                    \t\t td, th {padding:7px; padding-left:20px;padding-right:20px;border: 1px solid;}
                                                    \t </style>
                                                    </head>
                                                    <body>
                                                    """;
    private static final String ADD_TAGS_FINISH =
                                                """
                                                    "</body>
                                                    "
                                                    "</html>"
                                                    """;
    public static void writeTable(Table table) {
        if (!RESOURCES_DIR.isDirectory()) {
            RESOURCES_DIR.mkdir();
        }
        File htmlTable = new File(RESOURCES_DIR, "table_" + table.getName() + ".html");
        List<String> headers = table.getHeaders();
        List<List<Object>> values = table.getValues();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(htmlTable))) {
            bufferedWriter.write(ADD_TAGS_START);
            bufferedWriter.write("\t <h2>Table '" + table.getName() + "':</h2>\n");

            bufferedWriter.write("\t <table>\n");

                bufferedWriter.write("\t\t <tr>\n");
                for (String header : headers) {
                    bufferedWriter.write("\t\t\t <th>" + header + "</th>\n");
                }
                bufferedWriter.write("\t\t </tr>\n");

                for (List<Object> valueList : values) {
                    bufferedWriter.write("\t\t <tr>\n");
                    for (Object value : valueList) {
                        bufferedWriter.write("\t\t\t <td>" + value + "</td>\n");
                    }
                    bufferedWriter.write("\t\t </tr>\n");
                }

            bufferedWriter.write("\t </table>\n");
            bufferedWriter.write(ADD_TAGS_FINISH);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }


}
