package org.softwire.training.bookish.models.database;

public class Book {
    protected Author author;
    protected String title;
    protected long isbn;

    public Book(Author author, String title, long isbn) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public long getIsbn() {
        return isbn;
    }
}
