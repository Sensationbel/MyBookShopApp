package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.AuthorsDto;
import com.example.MyBookShopAPP.dto.BooksPageDto;
import com.example.MyBookShopAPP.dto.SearchWordDto;
import com.example.MyBookShopAPP.service.AuthorsService;
import com.example.MyBookShopAPP.service.ResourceStorage;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/authors")
@Api(description = "authors_data")
public class AuthorsController {

    private final AuthorsService authorsService;
    private final ResourceStorage storage;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("authorsMap")
    public Map<String, List<AuthorsDto>> authorsMap() {
        return authorsService.getAuthorsMap();
    }

    @GetMapping("")
    public String getAuthorsPage() {
        return "authors/index";
    }

    @GetMapping("/{slug}")
    public String getAuthors(@PathVariable("slug") String slug, Model model) {
        model.addAttribute("authorSlugDto", authorsService.getAuthorsSlugDtoById(slug));
        return "authors/slug";
    }

    @PostMapping("/{slug}/photo/save")
    public String saveNewAuthorsPhoto(@PathVariable("slug") String slug,
                                   @RequestParam("file") MultipartFile file) throws IOException {

        String folder = "/photo/";
        String savePath = storage.saveNewBookImage(file, slug, folder);
        authorsService.exchangePhoto(savePath, slug);
        return ("redirect:/authors/" + slug);
    }

}