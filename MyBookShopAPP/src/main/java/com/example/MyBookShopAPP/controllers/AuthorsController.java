package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.AuthorsDto;
import com.example.MyBookShopAPP.dto.BooksPageDto;
import com.example.MyBookShopAPP.dto.SearchWordDto;
import com.example.MyBookShopAPP.service.AuthorsService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
@Api(description = "authors_data")
public class AuthorsController {

    private final AuthorsService authorsService;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("authorsMap")
    public Map<String, List<AuthorsDto>> authorsMap() {
        return authorsService.getAuthorsMap();
    }

    @GetMapping("/authors")
    public String getAuthorsPage() {
        return "authors/index";
    }

    @GetMapping("/authors/{id}")
    public String getAuthors(@PathVariable("id") int id, Model model) {
        model.addAttribute("authorSlugDto", authorsService.getAuthorsSlugDtoById(id));
        return "authors/slug";
    }

    @GetMapping("/books/author/{id}")
    public String getBooksByAuthorId(@PathVariable("id") int id,
                                Model model) {
        model.addAttribute("authorDto", authorsService.getAuthorsDtoById(id));
        model.addAttribute("booksAuthor", authorsService.getBooksByAuthorId(0, 5, id));
        return "/books/author";
    }

    @GetMapping("/books/author/page/{id}")
    @ResponseBody
    public BooksPageDto getPageBooksByAuthorId(@PathVariable("id") int id,
                                               @RequestParam("offset") int offset,
                                               @RequestParam("limit") int limit) {
        return new BooksPageDto(authorsService.getBooksByAuthorId(offset, limit, id));
    }

}