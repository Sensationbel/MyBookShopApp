package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.AuthorsDto;
import com.example.MyBookShopAPP.dto.SearchWordDto;
import com.example.MyBookShopAPP.service.AuthorsService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
@Api(description = "authors_data")
public class AuthorsController {

    private final AuthorsService authorsService;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
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

}
