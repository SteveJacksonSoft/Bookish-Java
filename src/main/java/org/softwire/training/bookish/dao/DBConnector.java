package org.softwire.training.bookish.dao;

import org.jdbi.v3.core.Jdbi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static String hostname = "localhost";
    private static String database = "bookish";
    private static String user = "root";
    private static String password = "MSDev";
    public static String connectionString = "jdbc:mysql://" + hostname + "/" + database +
            "?user=" + user + "&password=" + password +
            "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT&useSSL=false";

    public static Jdbi getConnectedJdbi() {
        return Jdbi.create(connectionString);
    }

    public static Connection getConnector() throws SQLException {
        return DriverManager.getConnection(connectionString);
    }

}
