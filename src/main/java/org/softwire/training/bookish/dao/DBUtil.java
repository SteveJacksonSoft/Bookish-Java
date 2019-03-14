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
                handle.createQuery("SELECT id FROM Author WHERE authorName = :authorName")
                        .bind("authorName", authorName)
                        .mapTo(int.class)
                        .findFirst()
                        .orElseThrow(() ->
                                new SQLDataException("The author name '" + authorName + "' is not recognised.")
                        )
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
    static boolean bookDataIsInTable(long isbn) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM book WHERE isbn = :isbn")
                        .bind("isbn", isbn)
                        .map((resultSet, context) ->
                                resultSet.next()
                        )

                        .findFirst()
                        .orElse(false)
        );
    }

    static boolean authorIsInTable(String authorName) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT id FROM Author WHERE authorName = :authorName")
                        .bind("authorName", authorName)
                        .map((resultSet, context) ->
                                resultSet.next()
                        )
                        .findFirst()
                        .orElse(false)
        );
    }

    // Adding data to db
    static void addBookToTable(long isbn, String title) {
        jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO Book(isbn, title) VALUES (:isbn, :title)")
                        .bind("isbn", isbn)
                        .bind("title", title)
                        .execute()
        );
    }

    static void addAuthorToTable(String authorName) {
        jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO Author(authorName) VALUES (:authorName);")
                        .bind("authorName", authorName)
                        .execute()
        );
    }

    static void addBookAuthorRelationship(long isbn, String authorName) {
        try {
            int authorId = getAuthorIdAndAddIfNotPresent(authorName);
            jdbi.withHandle(handle ->
                    handle.createUpdate("INSERT INTO bookauthorrelationship(bookId, authorId) VALUES (:isbn, :authorId);")
                            .bind("isbn", isbn)
                            .bind("authorId", authorId)
                            .execute()
            );
        } catch (SQLDataException e) {
            LOGGER.error("The relationship between book isbn " + isbn +
                    " and author '" + authorName + "' could not be added to the database.");
        }
    }

    static void addBookCopyToTable(long isbn) {
        jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO BookCopy(isbn, checkedOutTo, dateDue) Values(:isbn, null, null);")
                        .bind("isbn", isbn)
                        .execute()
        );
    }
}
