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
            "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<style>" +
                    "body {font-family: Arial, Helvetica, sans-serif;}" +
                    "h2 {text-align: center;}" +
                    "table {margin: auto; text-align: center; border-collapse: collapse;}" +
                    "td, th {padding:7px; padding-left:20px;padding-right:20px;border: 1px solid;}"+
                    "</style>" +
                    "</head>" +
                    "<body>";
    private static final String ADD_TAGS_FINISH =
                    "</body>" +
                    "</html>";

    public static void writeTable(Table table) {
        if (!RESOURCES_DIR.isDirectory()) {
            RESOURCES_DIR.mkdir();
        }
        File htmlTable = new File(RESOURCES_DIR, "table_" + table.getName() + ".html");
        List<String> headers = table.getHeaders();
        List<List<Object>> values = table.getValues();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(htmlTable))) {
            bufferedWriter.write(ADD_TAGS_START);
            bufferedWriter.write("<h2>Table '" + table.getName() + "':</h2>");

            bufferedWriter.write("<table>");

            bufferedWriter.write("<tr>");
            for (String header : headers) {
                bufferedWriter.write("<th>" + header + "</th>");
            }
            bufferedWriter.write("<tr>");

            for (List<Object> valueList : values) {
                for (Object value : valueList) {
                    bufferedWriter.write("<td>" + value + "</td>");
                }
                bufferedWriter.write("<tr>");
            }

            bufferedWriter.write("</table>");
            bufferedWriter.write(ADD_TAGS_FINISH);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
