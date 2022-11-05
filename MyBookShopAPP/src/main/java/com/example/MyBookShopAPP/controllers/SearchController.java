package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.BooksPageDto;
import com.example.MyBookShopAPP.dto.SearchWordDto;
import com.example.MyBookShopAPP.errors.EmptySearchException;
import com.example.MyBookShopAPP.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final BookService bookService;


    @GetMapping(value = {"/", "/{searchWord}"})
    public String getSearchResult(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
                                  Model model) throws EmptySearchException {
        if(searchWordDto != null){
        model.addAttribute("searchWordDto", searchWordDto);
        model.addAttribute("searchResult",
                bookService.getPageOfSearchResultBooks(searchWordDto.getExample(),0, 5));
        return "/search/index";
        }else {
            throw new EmptySearchException("Search is not possible by null");
        }
    }

    @GetMapping("/page/{searchWord}")
    @ResponseBody
    public BooksPageDto recommendedBooksDto(@RequestParam("offset") Integer offset,
                                            @RequestParam("limit") Integer limit,
                                            @PathVariable(value = "searchWord") SearchWordDto searchWordDto){
        return new BooksPageDto(bookService.getPageOfSearchResultBooks(searchWordDto.getExample(),offset, limit));
    }
}
