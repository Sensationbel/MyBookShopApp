package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.GenresDto;
import com.example.MyBookShopAPP.dto.SearchWordDto;
import com.example.MyBookShopAPP.service.GenresService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenresController {

    private final GenresService genresService;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("listGenres")
    public List<GenresDto> listGenres(){
        return  genresService.getParentsListGenresEntity();
    }

    @GetMapping("/genres")
    public String genresBooksPage(){

        return "genres/index";
    }
}
