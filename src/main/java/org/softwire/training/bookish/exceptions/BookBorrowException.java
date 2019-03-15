package org.softwire.training.bookish.exceptions;

import org.softwire.training.bookish.models.database.BookCopy;

public class BookBorrowException extends Exception {
    private BookCopy bookCopy;

    public BookBorrowException() {
    }

    public BookBorrowException(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public BookBorrowException(String message) {
        super(message);
    }

    public BookBorrowException(String message, BookCopy bookCopy) {
        super(message);
        this.bookCopy = bookCopy;
    }
}
