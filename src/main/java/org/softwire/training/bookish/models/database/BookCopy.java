package org.softwire.training.bookish.models.database;

import org.softwire.training.bookish.exceptions.BookBorrowException;

import java.util.Date;

public class BookCopy extends Book {
    protected Integer borrowerId;
    protected Date dateDue;

    public BookCopy(Author author, String title, long isbn) {
        super(author, title, isbn);
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.borrowerId = null;
        this.dateDue = null;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public boolean isOnLoan() {
        return borrowerId != null;
    }

    public Date getDateDue() {
        return dateDue;
    }

    BookCopy borrow(int borrowerId) throws BookBorrowException {
        if (isOnLoan()) {
            throw new BookBorrowException("This book is already on loan! Please bring it back first.", this);
        }
        this.borrowerId = borrowerId;
        setDateDue();
        return this;
    }

    void bringBack() throws BookBorrowException {
        if (!isOnLoan()) {
            throw new BookBorrowException("BookCopy is not on loan anyway!", this);
        }
        this.borrowerId = null;
        resetDateDue();
    }

    private void setDateDue() {
        throw new RuntimeException("Not yet implemented.");
    }

    private void resetDateDue() {
        dateDue = null;
    }
}
