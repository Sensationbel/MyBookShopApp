package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.BooksDto;
import com.example.MyBookShopAPP.dto.BooksPageDto;
import com.example.MyBookShopAPP.dto.SearchWordDto;
import com.example.MyBookShopAPP.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecentController {

    private final BookService bookService;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("recentBooks")
    public List<BooksDto> recentBooks(){
        return bookService.getPageOfRecentBook(0, 5);
    }

    @GetMapping("/books/recent")
    public String recentBooksPage(){
        return "books/recent";
    }



    @GetMapping("/books/recent/page")
    @ResponseBody
    public BooksPageDto getResentBooksBetweenDate(@RequestParam(value = "from", required = false)
                                                      @DateTimeFormat(pattern = "dd.MM.yyyy") Date from,
                                                  @RequestParam(value = "to", required = false)
                                                  @DateTimeFormat(pattern = "dd.MM.yyyy") Date to,
                                                  @RequestParam("offset") Integer offset,
                                                  @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfRecentBooksBetween(from, to, offset, limit));
    }
}
