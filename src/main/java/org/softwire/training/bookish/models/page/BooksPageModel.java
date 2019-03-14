package org.softwire.training.bookish.models.page;

import org.softwire.training.bookish.models.web.BookForWeb;

import java.util.ArrayList;
import java.util.List;

public class BooksPageModel {
    private List<BookForWeb> books = new ArrayList<>();
    private boolean isError;

    public List<BookForWeb> getBooks() {
        return books;
    }

    public void setBooks(List<BookForWeb> books) {
        this.books = books;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
}
