package org.softwire.training.bookish;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.softwire.training.bookish.dao.DBConnector;
import org.softwire.training.bookish.dbinteractiontests.JDBCTest;
import org.softwire.training.bookish.dbinteractiontests.JDBITest;
import org.softwire.training.bookish.models.database.*;

import java.sql.SQLException;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Library library = new Library();

    public static String bookListingQuery = "SELECT book.title, GROUP_CONCAT(author.authorName SEPARATOR ', ') FROM book " +
            "INNER JOIN bookauthorRelationship ON book.isbn = bookauthorrelationship.bookId " +
            "INNER JOIN author ON author.id = bookauthorrelationship.authorId GROUP BY book.title;";

    public static void main(String[] args) throws SQLException {
        jdbcMethod(DBConnector.connectionString);
        jdbiMethod(DBConnector.connectionString);

        library.addBook(new BookCopy(new Author("Jim Hamilton"), "Book Number 1", 33492855012L));

    }

    private static void jdbcMethod(String connectionString) throws SQLException {
        System.out.println("JDBC method...");

        // See this page for details: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html

        JDBCTest.printBooksOrderedByTitle().forEach(System.out::println);



    }

    private static void jdbiMethod(String connectionString) {
        System.out.println("\nJDBI method...");

        // TODO: print out the details of all the books (using JDBI)
        // See this page for details: http://jdbi.org
        // Use the "BookCopy" class that we've created for you (in the models.database folder)


        JDBITest.printBooksOrderedByTitle(connectionString).forEach(System.out::println);
    }
}
