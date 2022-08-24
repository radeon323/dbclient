package com.olshevchenko.dbclient;

import com.olshevchenko.dbclient.entity.QueryResult;
import com.olshevchenko.dbclient.service.QueryHandler;
import com.olshevchenko.dbclient.service.QueryResultConsoleWriter;
import com.olshevchenko.dbclient.service.QueryResultHtmlWriter;
import com.olshevchenko.dbclient.utils.DriverConfig;
import com.olshevchenko.dbclient.utils.PropertiesReader;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author Oleksandr Shevchenko
 */
public class Starter {

    public static void main(String[] args) {
        Properties properties = PropertiesReader.getProperties("application.properties");
        String pathToHtmlResult = properties.getProperty("report.html");

        DriverConfig driverConfig = new DriverConfig(properties);
        DataSource driver = driverConfig.dataSource();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter your query: ");
            String query = scanner.nextLine();

            QueryHandler queryHandler = new QueryHandler(driver);
            QueryResult queryResult = queryHandler.handle(query);

            QueryResultConsoleWriter consoleWriter = new QueryResultConsoleWriter(queryResult);
            consoleWriter.writeResult();

            QueryResultHtmlWriter htmlWriter = new QueryResultHtmlWriter(queryResult, pathToHtmlResult);
            htmlWriter.writeResult();
        }
    }


}
