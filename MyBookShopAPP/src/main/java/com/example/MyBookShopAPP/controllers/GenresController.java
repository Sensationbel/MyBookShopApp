package com.example.MyBookShopAPP.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("booksShop")
public class GenresController {

    @GetMapping("/genres")
    public String genres(){
        return "genres/index";
    }
}
