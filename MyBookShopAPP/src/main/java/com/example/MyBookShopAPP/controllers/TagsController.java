package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.BooksPageDto;
import com.example.MyBookShopAPP.dto.SearchWordDto;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.GenresInterfaces;
import com.example.MyBookShopAPP.service.GenresService;
import com.example.MyBookShopAPP.service.TagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class TagsController {

    private final GenresService genresService;
    private final TagsService tagsService;
    private final GenresInterfaces gi;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @GetMapping("/tags/{slug}")
    public String tagsPage(@PathVariable("slug") String slug, Model model){
        model.addAttribute("listBooks", genresService.getBooksDtoBySlug(0, 5, slug));
        model.addAttribute("tagsDTO", tagsService.getTagsDtoBySlug(slug));
        return "tags/index";
    }

    @GetMapping("/books/tag/{slug}")
    @ResponseBody
    public BooksPageDto tagsPageBySlug(@PathVariable("slug") String slug,
                                       @RequestParam("offset") int offset,
                                       @RequestParam("limit") int limit){
        return new BooksPageDto(genresService.getBooksDtoBySlug(offset, limit, slug));
    }

}
