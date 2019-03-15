package org.softwire.training.bookish.models.database;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private long id;
    private String name;
    private List<BookCopy> booksCheckedOut;

    public Member(long id, String name) {
        this.id = id;
        this.name = name;
        this.booksCheckedOut = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BookCopy> getBooksCheckedOut() {
        return booksCheckedOut;
    }

    String changeName(String newName) {
        this.name = newName;
        return newName;
    }
}
