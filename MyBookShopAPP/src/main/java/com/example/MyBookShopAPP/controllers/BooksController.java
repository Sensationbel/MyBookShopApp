package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.*;
import com.example.MyBookShopAPP.service.BookService;
import com.example.MyBookShopAPP.service.TagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BooksController {

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

    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getRecommendedBooks(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfRecommendedBook(offset, limit));
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResult(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
                                  Model model){
        model.addAttribute("searchWordDto", searchWordDto);
        model.addAttribute("searchResult",
                bookService.getPageOfSearchResultBooks(searchWordDto.getExample(),0, 5));
        return "/search/index";
    }

    @GetMapping("search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto recommendedBooksDto(@RequestParam("offset") Integer offset,
                                            @RequestParam("limit") Integer limit,
                                            @PathVariable(value = "searchWord") SearchWordDto searchWordDto){
        return new BooksPageDto(bookService.getPageOfSearchResultBooks(searchWordDto.getExample(),offset, limit));
    }

    @GetMapping("/books/popular/main")
    @ResponseBody
    public BooksPageDto getPopularBooks(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfPopularBook(offset, limit));
    }

    @GetMapping("/books/recent/main")
    @ResponseBody
    public BooksPageDto getRecentBooks(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfRecentBook(offset, limit));
    }




}
