package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.BooksDto;
import com.example.MyBookShopAPP.dto.SearchWordDto;
import com.example.MyBookShopAPP.dto.TagsDto;
import com.example.MyBookShopAPP.service.BookService;
import com.example.MyBookShopAPP.service.TagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final BookService bookService;

    private final TagsService tagsService;

    @ModelAttribute("recommendedBooks")
    public List<BooksDto> recommendedBooks(){
        return bookService.getPageOfRecommendedBook(0, 6);
    }

    @ModelAttribute("popularBooks")
    public List<BooksDto> popularBooks(){
        return bookService.getPageOfPopularBook(0, 6);
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResult")
    public List<BooksDto> searchResultList(){
        return new ArrayList<>();
    }

    @ModelAttribute("recentBooks")
    public List<BooksDto> recentBooks(){
        return bookService.getPageOfRecentBook(0, 6);
    }

    @ModelAttribute("tagsList")
    public List<TagsDto> genresTagList(){
        return tagsService.getPageOfTags();
    }

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }
}
