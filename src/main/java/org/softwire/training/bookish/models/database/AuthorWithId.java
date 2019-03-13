package org.softwire.training.bookish.models.database;

public class AuthorWithId extends Author {
    private int id;

    public AuthorWithId(String name, int id) {
        super(name);
        this.id = id;
    }

    public AuthorWithId(Author author, int id) {
        super(author.getName());
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
