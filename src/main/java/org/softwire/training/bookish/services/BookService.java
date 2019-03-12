package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.BookCopy;

import java.util.List;

public class BookService {
    public static List<BookCopy> getBooksByAuthor(String author) {}

    public static List<BookCopy> getBooksByTitle(String title) {}

    public static List<BookCopy> getBooksByISBN(String isbn) {}

    public static void addBookToDB(BookCopy bookCopy) {}

    public static void updateBookInDB(BookCopy bookCopy) {}

    public static void checkoutBook(BookCopy bookCopy) {}

    public static void returnBook(BookCopy bookCopy) {}
}
