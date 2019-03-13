package org.softwire.training.bookish.dbinteractiontests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.softwire.training.bookish.Main;
import org.softwire.training.bookish.dao.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCTest {
    private static final Logger LOGGER = LogManager.getLogger();

    public static List<String> printBooksOrderedByTitle() {
        try (Statement statement = DBConnector.getConnector().createStatement()) {
            ResultSet resultSet = statement.executeQuery(Main.bookListingQuery);
            List<String> dbReadOut = new ArrayList<>();
            String format = "%20s : %-20s";
            dbReadOut.add(String.format(format, "BookCopy title", "Author(s)"));
            while (resultSet.next()) {
                dbReadOut.add(String.format(format, resultSet.getString(1), resultSet.getString(2)));
            }
            return dbReadOut;
        } catch (SQLException e) {
            LOGGER.error("The connection to the server didn't work.", e);
            System.out.println("There was a problem with the connection to the database.");
            return new ArrayList<>();
        }
    }
}
