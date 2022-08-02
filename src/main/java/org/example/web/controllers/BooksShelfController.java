package org.example.web.controllers;

import lombok.extern.log4j.Log4j;
import org.example.app.exeptions.BookShelfException;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Log4j
@Controller
@RequestMapping(value = "books")
@Scope("singleton")
public class BooksShelfController {

    private final BookService bookService;

    @Autowired
    public BooksShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model){
        log.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model){
        log.info("hasErrors: " + bindingResult.hasErrors() + " " + book.getSize());
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }else {
            log.info("current repository size: " + bookService.getAllBooks().size());
            bookService.saveBook(book);
            return "redirect:/books/shelf";
        }

    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            log.info("id not found");
            return "book_shelf";
        } else {bookService.removeBookId(bookIdToRemove.getId());
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

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception{
        if(!file.isEmpty()){
            String name = file.getOriginalFilename();
            byte[] bytes = file.getBytes();

            String rootPath = System.getProperty("catalina.home");
            File dir = new File(rootPath + File.separator + "external_uploads");
            if(dir.exists()){
                dir.mkdirs();
            }

            File serverFiles = new File(dir.getAbsolutePath() + File.separator + name);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFiles));
            stream.write(bytes);
            stream.close();

            log.info("new file saved at: " + serverFiles.getAbsolutePath());
            return "redirect:/books/shelf";

        }else {
            throw new BookShelfException("no file selected");
        }

    }

    @ExceptionHandler(BookShelfException.class)
    public String handleError(Model model, BookShelfException exception){
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/500";
    }
}
