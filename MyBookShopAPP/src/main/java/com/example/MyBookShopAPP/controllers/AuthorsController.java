package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.service.AuthorsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("booksShop")
@Log4j2
@RequiredArgsConstructor
public class AuthorsController {

    private final AuthorsService authorsService;

    @GetMapping("/authors")
    public String authors(Model model){
        model.addAttribute("authorsData", authorsService.getAuthorsData());
        model.addAttribute("searchPlaceholder", "new search placeholder");
        model.addAttribute("serverTime", new Date());
        model.addAttribute("placeholderTextPart2", "server");
        model.addAttribute("messageTemplate", "searchbar.placeholder2");
        return "authors/index";
    }


}
