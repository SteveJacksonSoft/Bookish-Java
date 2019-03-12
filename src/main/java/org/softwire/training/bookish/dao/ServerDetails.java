package org.softwire.training.bookish.dao;


import java.sql.*;

public class ServerDetails {
    private static String hostname = "localhost";
    private static String database = "bookish";
    private static String user = "root";
    private static String password = "MSDev";
    public static String connectionString = "jdbc:mysql://" + hostname + "/" + database +
            "?user=" + user + "&password=" + password +
            "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT&useSSL=false";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString);
    }

}
