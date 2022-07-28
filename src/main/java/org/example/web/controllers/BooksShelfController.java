package org.example.web.controllers;

import lombok.extern.log4j.Log4j;
import org.example.app.services.BookRepository;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j
@Controller
@RequestMapping(value = "books")
public class BooksShelfController {

    private final BookService bookService;

    @Autowired
    public BooksShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model){
        log.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book){
        if (bookService.checkBookOfEmpty(book)) {
            bookService.saveBook(book);
            log.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
        log.info("book is empty.");
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "bookIdToRemove") Integer bookIdRemove){
        if(bookService.removeBookId(bookIdRemove)){
            return "redirect:/books/shelf";
        }else{
            log.info("id: " + bookIdRemove + " not found");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeByRegex")
    public String removeBooksByRegex(@RequestParam(value = "queryRegex") String regex){
        if(bookService.removeBookRegex(regex)){
            return "redirect:/books/shelf";
        }else{
            log.info("book by " + regex + " not found");
            return "redirect:/books/shelf";
        }
    }
}
