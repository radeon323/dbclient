package com.olshevchenko.dbclient;

import com.olshevchenko.dbclient.service.QueryHandler;
import com.olshevchenko.dbclient.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @author Oleksandr Shevchenko
 */
public class Starter {

    public static void main(String[] args) {
        try (Connection connection = ConnectionManager.getConnection();) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter your query: ");
                String query = scanner.nextLine();
                try (Statement statement = connection.createStatement()) {
                    QueryHandler queryHandler = new QueryHandler(statement, query);
                    queryHandler.handle();
                } catch (SQLException e) {
                    throw new RuntimeException("Database command execution error", e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database access error", e);
        }


    }


}
