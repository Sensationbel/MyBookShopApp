package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.BooksDto;
import com.example.MyBookShopAPP.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PopularsController {

    private final BookService bookService;

//    @ModelAttribute("booksList")
//    public List<BooksDto> getBooksData(){
//        return bookService.getBooksData().stream().limit(20).toList();
//    }

    @GetMapping("/books/popular")
    public String popularBooksPage(){
        return "books/popular";
    }
}
