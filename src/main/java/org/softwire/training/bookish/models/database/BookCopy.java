package org.softwire.training.bookish.models.database;

import org.softwire.training.bookish.exceptions.BookBorrowException;

import java.util.Date;

public class BookCopy extends Book {
    private long bookId;
    private boolean isOnLoan;
    private Member borrower;
    private Date dateDue;

    public BookCopy(long bookId, String author, String title, String isbn) {
        this.bookId = bookId;
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.isOnLoan = false;
        this.borrower = null;
    }

    public long getBookId() {
        return bookId;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isOnLoan() {
        return isOnLoan;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    BookCopy borrow(Member borrower) throws BookBorrowException {
        if (isOnLoan) {
            throw new BookBorrowException("This book is already on loan! Please bring it back first.", this);
        }
        isOnLoan = true;
        this.borrower = borrower;
        setDateDue();
        return this;
    }

    void bringBack() throws BookBorrowException {
        if (!isOnLoan) {
            throw new BookBorrowException("BookCopy is not on loan anyway!", this);
        }
        isOnLoan = false;
        this.borrower = null;
        resetDateDue();
    }

    private void setDateDue() {
        throw new RuntimeException("Not yet implemented.");
    }

    private void resetDateDue() {
        dateDue = null;
    }
}
