package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BooksController {

    private final BookService bookService;

//    @ModelAttribute("recommendedBooks")
//    public List<BooksDto> recommendedBooks(){
//        return bookService.getBooksData();
//    }

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }

}
