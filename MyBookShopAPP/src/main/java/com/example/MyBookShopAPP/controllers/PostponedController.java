package com.example.MyBookShopAPP.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostponedController {

    @GetMapping("/postponed")
    public String getPostponedBooksPage(){
        return "postponed";
    }
}
