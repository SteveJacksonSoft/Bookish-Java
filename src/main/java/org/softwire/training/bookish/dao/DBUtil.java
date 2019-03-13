package org.softwire.training.bookish.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.Jdbi;
import org.softwire.training.bookish.models.database.Book;
import org.softwire.training.bookish.models.database.BookCopy;
import org.softwire.training.bookish.models.database.BookCopyData;

import java.sql.SQLDataException;
import java.util.List;

class DBUtil {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Jdbi jdbi = DBConnector.getConnectedJdbi();


    // Getting data from db
    public static List<BookCopyData> getBooksByTitle(String title) {
        return getBooksByProperty("book.title", title);
    }

    public static List<BookCopyData> getBooksByAuthorName(String authorName) {
        return getBooksByProperty("author.authorName", authorName);
    }

    public static List<BookCopyData> getBooksByIsbn(long isbn) {
        return getBooksByProperty("book.isbn", isbn);
    }

    private static List<BookCopyData> getBooksByProperty(String propertyName, String propertyValue) {
        return jdbi.withHandle(handle ->
                handle.createQuery(SQLCommands.bookViewCommand)
                        .bind("propertyName", propertyName)
                        .bind("propertyValue", propertyValue)
                        .map(BookCopyData.JdbiConverter)
                        .list()
        );
    }

    private static List<BookCopyData> getBooksByProperty(String propertyName, long propertyValue) {
        return jdbi.withHandle(handle ->
                handle.createQuery(SQLCommands.bookViewCommand)
                        .bind("propertyName", propertyName)
                        .bind("propertyValue", propertyValue)
                        .map(BookCopyData.JdbiConverter)
                        .list()
        );
    }

    private static int getAuthorIdAndAddIfNotPresent(String authorName) throws SQLDataException {
        try {
            if (!DBUtil.authorIsInTable(authorName)) {
                DBUtil.addAuthorToTable(authorName);
            }
            return getAuthorId(authorName);
        } catch (SQLDataException e) {
            LOGGER.error("The author name '" + authorName +
                    "' was not recognised and could not be added to the database.", e);
            throw new SQLDataException(e);
        }
    }

    private static int getAuthorId(String authorName) throws SQLDataException {
        return jdbi.withHandle(handle ->
                handle.createQuery(SQLCommands.getAuthorIdByName)
                        .bind("name", authorName)
                        .mapTo(int.class)
                        .findFirst()
        )
                .orElseThrow(() ->
                        new SQLDataException("The author name '" + authorName + "' is not recognised.")
                );
    }

//    static void updateBookProperty(long isbn, String propertyName, String propertyValue) {
//        
//    }


    // Updating
    static void updateBookTitle(long isbn, String newTitle) {
        jdbi.withHandle(handle -> 
                handle.createQuery(SQLCommands.updateBookTitle)
                        .bind("isbn", isbn)
                        .bind("newTitle", newTitle)
        );
    }

    static void updateBookAuthorRelationship(long isbn, String authorName) throws SQLDataException {
        int authorId = getAuthorIdAndAddIfNotPresent(authorName);
        jdbi.withHandle(handle -> 
                handle.createQuery(SQLCommands.addNewBookAuthorRelationship)
                        .bind("isbn", isbn)
                        .bind("authorId", authorId)
        );
    }


    // Check current data in db
    static boolean bookDataIsInTable(BookCopy bookCopy) {
        return jdbi.withHandle(handle ->
                handle.createQuery(SQLCommands.bookViewCommand)
                        .bind("propertyName", "book.isbn")
                        .bind("propertyValue", bookCopy.getIsbn())
                        .map((resultSet, context) ->
                                resultSet.next()
                        )
                        .findOnly()
        );
    }

    static boolean authorIsInTable(String authorName) {
        return jdbi.withHandle(handle ->
                handle.createQuery(SQLCommands.getAuthorIdByName)
                        .bind("authorName", authorName)
                        .map((resultSet, context) ->
                                resultSet.next()
                        )
                        .findOnly()
        );
    }


    // Adding data to db
    static void addBookToTable(BookCopy bookCopy) {
        jdbi.withHandle(handle ->
                handle.createQuery(SQLCommands.addNewBook)
                        .bind("isbn", bookCopy.getIsbn())
                        .bind("title", bookCopy.getTitle())
        );
    }

    static void addAuthorToTable(String authorName) {
        jdbi.withHandle(handle ->
                handle.createQuery(SQLCommands.addNewAuthor)
                        .bind("authorName", authorName)
        );
    }

    static void addBookAuthorRelationship(Book book, String authorName) {
        try {
            int authorId = getAuthorIdAndAddIfNotPresent(authorName);
            jdbi.withHandle(handle ->
                    handle.createQuery(SQLCommands.addNewBookAuthorRelationship)
                            .bind("isbn", book.getIsbn())
                            .bind("authorId", authorId)
            );
        } catch (SQLDataException e) {
            LOGGER.error("The relationship between book isbn " + book.getIsbn() +
                    " and author '" + authorName + "' could not be added to the database.");
        }
    }

    static void addBookCopyToTable(BookCopy bookCopy) {
        jdbi.withHandle(handle ->
                handle.createQuery(SQLCommands.addBookCopy)
                        .bind("isbn", bookCopy.getIsbn())
        );
    }
}
