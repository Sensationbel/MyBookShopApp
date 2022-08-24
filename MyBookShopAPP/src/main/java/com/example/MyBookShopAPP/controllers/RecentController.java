package com.example.MyBookShopAPP.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecentController {

    @GetMapping("/books/recent")
    public String recentBooksPage(){
        return "books/recent";
    }
}
