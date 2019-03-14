package org.softwire.training.bookish.dao;

import org.jdbi.v3.core.Jdbi;
import org.softwire.training.bookish.exceptions.IsbnException;
import org.softwire.training.bookish.models.web.BookForWeb;

import java.util.List;

public class BooksDao {
    Jdbi jdbi = DBConnector.getConnectedJdbi();

    public List<BookForWeb> getAllBookDetails() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT book.isbn, book.title, group_concat(author.authorName SEPARATOR \", \") " +
                        "AS authors  From book LEFT JOIN bookauthorrelationship ON book.isbn=bookauthorrelationship.bookId " +
                        "LEFT JOIN author ON bookauthorrelationship.authorId=author.id " +
                        "GROUP BY book.isbn;")
                .mapToBean(BookForWeb.class)
                .list()
        );
    }

    public void addBookTemplate(BookForWeb book) throws IsbnException{
        if (DBUtil.bookDataIsInTable(book.getIsbn())) {
            throw new IsbnException("A book with ISBN " + book.getIsbn() + "is already in the database.");
        } else {
            DBUtil.addBookToTable(book.getIsbn(), book.getTitle());
            for (String authorName: book.getAuthors().split("\\s*,\\s*")) {
                if (!DBUtil.authorIsInTable(authorName)) {
                    DBUtil.addAuthorToTable(authorName);
                }
                DBUtil.addBookAuthorRelationship(book.getIsbn(), authorName);
            };
        }
    }

    public void addBookCopy(BookForWeb book) throws IsbnException {
        if (!DBUtil.bookDataIsInTable(book.getIsbn())) {
            throw new IsbnException("There is no book in the database with isbn " + book.getIsbn() +
                    " - you may only add copies of book templates already in the database.");
        } else {
            DBUtil.addBookCopyToTable(book.getIsbn());
        }
    }

}
