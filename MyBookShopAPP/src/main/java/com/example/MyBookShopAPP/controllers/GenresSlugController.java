package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.BooksPageDto;
import com.example.MyBookShopAPP.dto.SearchWordDto;
import com.example.MyBookShopAPP.service.GenresService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class GenresSlugController {

    private final GenresService genresService;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @GetMapping("/genres/{slug}")
    public String genresBySlug(@PathVariable("slug") String slug, Model model){
        model.addAttribute("searchWordDto", new SearchWordDto());
        model.addAttribute("booksList", genresService.getBooksDtoBySlug(0, 5, slug));
        model.addAttribute("slugAndName", genresService.getGenresSlugAndName(slug));
        return "genres/slug";
    }

    @GetMapping("/books/genre/{slug}")
    @ResponseBody
    public BooksPageDto genresBySlug(@PathVariable("slug") String slug,
                                   @RequestParam("offset") int offset,
                                   @RequestParam("limit") int limit){
        return new BooksPageDto(genresService.getBooksDtoBySlug(offset, limit, slug));
    }

}
