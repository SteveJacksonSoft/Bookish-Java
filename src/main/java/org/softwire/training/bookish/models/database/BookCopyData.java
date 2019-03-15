package org.softwire.training.bookish.models.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.mapper.RowMapper;

import java.sql.SQLException;
import java.util.Date;

public class BookCopyData extends BookCopy {
    private static final Logger LOGGER = LogManager.getLogger();

    public static RowMapper<BookCopyData> JdbiConverter = (resultSet, context) -> {
        try {
            resultSet.next();
            String title = resultSet.getString("title");
            String authorName = resultSet.getString("author.authorName");
            long isbn = resultSet.getLong("isbn");
            Date dateDue = resultSet.getDate("dateDue");
            int borrowerId = resultSet.getInt("checkedOutTo");
            int copyId = resultSet.getInt("id");
            return new BookCopyData(new Author(authorName), title, isbn, dateDue, borrowerId, copyId);
        } catch (SQLException e) {
            LOGGER.error("There was a problem converting the result set received from the database " +
                    "to a BookCopyData object", e);
            throw new RuntimeException("");
        }
    };

    private int copyId;

    public BookCopyData(Author author, String title, long isbn, Date datedue, int borrowerId, int copyId) {
        super(author, title, isbn);
        this.dateDue = datedue;
        this.borrowerId = borrowerId;
        this.copyId = copyId;
    }

    public BookCopyData(BookCopy bookCopy, int copyId) {
        super(bookCopy.getAuthor(), bookCopy.getTitle(), bookCopy.getIsbn());
        this.copyId = copyId;
    }

    public long getCopyId() {
        return copyId;
    }
}
