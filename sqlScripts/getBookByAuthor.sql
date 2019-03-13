USE bookish;
SELECT * FROM Book LEFT JOIN BookCopy ON Book.isbn = BookCopy.isbn
                   LEFT JOIN BookAuthorRelationship ON Book.isbn = BookAuthorRelationship.bookId
                   LEFT JOIN Author ON bookauthorrelationship.authorId = author.id
                   WHERE Author.authorName = 'Jim Hamilton';