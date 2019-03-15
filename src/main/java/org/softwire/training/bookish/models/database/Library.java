package org.softwire.training.bookish.models.database;

import java.util.List;

import org.softwire.training.bookish.dao.DBInteraction;
import org.softwire.training.bookish.services.*;

public class Library {

    public void addBook(BookCopy bookCopy) {
        DBInteraction.addBookToDB(bookCopy);
    }

    public List<BookCopyData> findBooksByAuthor(String author) {
        return BookService.getBooksByAuthorName(author);
    }

    public List<BookCopyData> findBooksByTitle(String title) {
        return BookService.getBooksByTitle(title);
    }

    public List<BookCopyData> findBooksByIsbn(long isbn) {
        return BookService.getBooksByISBN(isbn);
    }

    public void addMember(Member member) {
        MemberService.addNewMemberToDB(member);
    }

    public Member findMember(Long memberId) {
        return MemberService.findMemberByID(memberId);
    }

    public List<Member> findMembersByName(String memberName) {
        return MemberService.findMemberByName(memberName);
    }
}
