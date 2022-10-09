package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.BooksDto;
import com.example.MyBookShopAPP.dto.BooksPageDto;
import com.example.MyBookShopAPP.dto.SearchWordDto;
import com.example.MyBookShopAPP.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PopularsController {

    private final BookService bookService;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("popularBooks")
    public List<BooksDto> popularsBooks(){
        return bookService.getPageOfPopularBook(0, 5);
    }

    @GetMapping("/books/popular")
    public String popularBooksPage(){
        return "books/popular";
    }

    @GetMapping("/books/popular/page")
    @ResponseBody
    public BooksPageDto getPopularBooks(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfPopularBook(offset, limit));
    }
}
