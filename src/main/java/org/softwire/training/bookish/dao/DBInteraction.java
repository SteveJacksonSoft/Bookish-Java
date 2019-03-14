package org.softwire.training.bookish.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.softwire.training.bookish.models.database.BookCopy;
import org.softwire.training.bookish.models.database.BookCopyData;

import java.sql.SQLDataException;
import java.util.List;

public class DBInteraction {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void addBookToDB(BookCopy bookCopy) {
        if (!DBUtil.bookDataIsInTable(bookCopy.getIsbn())) {
            DBUtil.addBookToTable(bookCopy.getIsbn(), bookCopy.getTitle());
            if (!DBUtil.authorIsInTable(bookCopy.getAuthor().getName())) {
                DBUtil.addAuthorToTable(bookCopy.getAuthor().getName());
            }
            DBUtil.addBookAuthorRelationship(bookCopy.getIsbn(), bookCopy.getAuthor().getName());
        }
        DBUtil.addBookCopyToTable(bookCopy.getIsbn());
    }

    public static List<BookCopyData> getBooksByTitle(String title) {
        return DBUtil.getBooksByTitle(title);
    }

    public static List<BookCopyData> getBooksByAuthorName(String authorName) {
        return DBUtil.getBooksByAuthorName(authorName);
    }

    public static List<BookCopyData> getBooksByIsbn(long isbn) {
        return DBUtil.getBooksByIsbn(isbn);
    }

    public static void updateBookCopyDetails(long isbn, String newTitle, String newAuthorName) {
        try {
            DBUtil.updateBookTitle(isbn, newTitle);
            DBUtil.updateBookAuthorRelationship(isbn, newAuthorName);
        } catch (SQLDataException e) {
            LOGGER.error("There was a problem updating the book record '" + isbn +
                    "' to the library. The author name '" + newAuthorName +
                    "' was not recognised and could not be added to the database.", e);
        }
    }

}
