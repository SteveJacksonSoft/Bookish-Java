package org.softwire.training.bookish.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.softwire.training.bookish.dao.BooksDao;
import org.softwire.training.bookish.exceptions.IsbnException;
import org.softwire.training.bookish.models.web.BookForWeb;
import org.softwire.training.bookish.models.page.BooksPageModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private static final Logger LOGGER = LogManager.getLogger();
    private BooksDao booksDao = new BooksDao();


    @RequestMapping("")
    ModelAndView bookPage() {
        return new ModelAndView("books", "model", populateModel(false));
    }

    @RequestMapping("/add-book")
    RedirectView addBook(@ModelAttribute BookForWeb book) {

        try {
            booksDao.addBookTemplate(book);
            booksDao.addBookCopy(book);
        } catch (IsbnException e) {
            LOGGER.error("There was a problem adding the book " + book.getIsbn() + ", " + book.getTitle() + " to the database.", e);
            return new RedirectView("/books/error");
        }

        return new RedirectView("/books");
    }

    @RequestMapping("/error")
    ModelAndView errorBookPage() {
        return new ModelAndView("books", "model", populateModel(true));
    }

    private BooksPageModel populateModel(boolean isError) {
        List<BookForWeb> books = booksDao.getAllBookDetails();

        BooksPageModel booksPageModel = new BooksPageModel();
        booksPageModel.setBooks(books);
        booksPageModel.setError(isError);
        return booksPageModel;
    }

    //TODO: add functionality - show book details on click
    //TODO: add functionality - checkin/out book
    //TODO: add functionality - edit book details
}
