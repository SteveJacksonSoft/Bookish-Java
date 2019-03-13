USE bookish;
SELECT * FROM Book LEFT JOIN BookCopy ON Book.isbn = BookCopy.isbn WHERE book.isbn = :isbn;