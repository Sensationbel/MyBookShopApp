package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.data.Book;
import com.example.MyBookShopAPP.dto.BooksDto;
import com.example.MyBookShopAPP.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    private final BookService bookService;

    @ModelAttribute("recommendedBooks")
    public List<BooksDto> recommendedBooks(){
        return bookService.getBooksData();
    }

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }

}
