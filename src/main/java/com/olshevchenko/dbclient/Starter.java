package com.olshevchenko.dbclient;

import com.olshevchenko.dbclient.service.QueryHandler;
import com.olshevchenko.dbclient.utils.PropertiesReader;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.Properties;
import java.util.Scanner;

/**
 * @author Oleksandr Shevchenko
 */
public class Starter {

    public static void main(String[] args) {
        Properties properties = PropertiesReader.getProperties();
        final String jdbcName = properties.getProperty("jdbc_name");
        final String jdbcUser = properties.getProperty("jdbc_user");
        final String jdbcPassword = properties.getProperty("jdbc_password");

        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setDatabaseName(jdbcName);
        pgSimpleDataSource.setUser(jdbcUser);
        pgSimpleDataSource.setPassword(jdbcPassword);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter your query: ");
            String query = scanner.nextLine();
                QueryHandler queryHandler = new QueryHandler(pgSimpleDataSource, query);
                queryHandler.handle();
        }
    }


}
