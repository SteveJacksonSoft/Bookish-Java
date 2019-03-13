package org.softwire.training.bookish.dao;

public class SQLCommands {

    // Add Members:
    public static String addMember = "USE bookish;\n" +
            "INSERT INTO Member(memberName) VALUES (:memberName);";


    // Find members:
    public static String getMemberByName = "USE bookish;\n" +
            "SELECT * FROM member WHERE memberName = :name;";


    public static String getMemberById = "USE bookish;\n" +
            "SELECT * FROM Member WHERE id = :id;";


    // Add books and related:
    public static String addNewBookAuthorRelationship = "USE bookish;\n" +
            "INSERT INTO bookauthorrelationship VALUES (:isbn, :authorId);";

    public static String addNewBook = "USE bookish;\n" +
            "INSERT INTO Book(isbn, title) VALUES (:isbn, :title);\n";

    public static String addNewAuthor = "USE bookish;\n" +
            "INSERT INTO Author(authorName) VALUES (:authorName);";

    public static String addBookCopy = "USE bookish;\n" +
            "INSERT INTO BookCopy(isbn, checkedOutTo, dateDue) Values(:isbn, null, null);";


    // Find books:
    public static String getBookCopyById = "USE bookish;\n" +
            "SELECT * FROM Book WHERE isbn = :isbn;";

    public static String bookViewCommand = "USE bookish; SELECT * FROM Book LEFT JOIN BookCopy ON Book.isbn = BookCopy.isbn\n" +
            "LEFT JOIN BookAuthorRelationship ON Book.isbn = BookAuthorRelationship.bookId\n" +
            "LEFT JOIN Author ON bookauthorrelationship.authorId = author.id\n" +
            "WHERE :propertyName = :propertyValue;";


    // Get ids etc:
    public static String getAuthorIdByName = "USE bookish;\n" +
            "SELECT * FROM Author WHERE authorName = :authorName";


    // Alter books details:
    public static String updateBookTitle = "USE bookish;\n" +
            "UPDATE book SET title = :newTitle WHERE isbn = :isbn;";

    public static String updateBookAuthor = "USE bookish;\n" +
            "UPDATE bookauthorrelationship SET authorId = :newAuthorId WHERE bookId = :isbn;";


    // Update loaning details
    public static String checkOutBook = "USE bookish;\n" +
            "UPDATE BookCopy SET checkedOutTo = :memberId, dateDue = :dateDue WHERE id = :bookId;";

    public static String returnBook = "USE bookish;\n" +
            "UPDATE BookCopy SET checkedOutTo = null, dateDue = null WHERE id = :bookId;";

}
