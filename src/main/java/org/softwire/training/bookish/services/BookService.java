package org.softwire.training.bookish.services;

import org.softwire.training.bookish.dao.DBInteraction;
import org.softwire.training.bookish.models.database.BookCopy;
import org.softwire.training.bookish.models.database.BookCopyData;

import java.util.List;

public class BookService {
    public static List<BookCopyData> getBooksByAuthorName(String author) {
        return DBInteraction.getBooksByAuthorName(author);
    }

    public static List<BookCopyData> getBooksByTitle(String title) {
        return DBInteraction.getBooksByTitle(title);
    }

    public static List<BookCopyData> getBooksByISBN(long isbn) {
        return DBInteraction.getBooksByIsbn(isbn);
    }

    public static void updateBookInDB(int bookId, BookCopy bookCopy) {
        DBInteraction.updateBookCopyDetails(bookId, bookCopy.getTitle(), bookCopy.getAuthor().getName());
    }

    public static void checkoutBook(BookCopy bookCopy) {
        throw new RuntimeException("Not yet implemented.");
    }

    public static void returnBook(BookCopy bookCopy) {
        throw new RuntimeException("Not yet implemented.");
    }
}
