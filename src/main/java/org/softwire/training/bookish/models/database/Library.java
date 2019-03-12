package org.softwire.training.bookish.models.database;

import java.util.List;
import org.softwire.training.bookish.services.*;

public class Library {

    public List<BookCopy> findBooksByAuthor(String author) {
        return BookService.getBooksByAuthor(author);
    }

    public List<BookCopy> findBooksByTitle(String title) {
        return BookService.getBooksByTitle(title);
    }

    public List<BookCopy> findBooksByIsbn(String isbn) {
        return BookService.getBooksByISBN(isbn);
    }

    public Member findMember(Long memberId) {
        return MemberService.findMemberByID(memberId);
    }

    void addMember(Member member) {
        MemberService.addNewMemberToDB(member);
    }

    void addBook(BookCopy bookCopy) {
        BookService.addBookToDB(bookCopy);
    }
}
