package com.example.MyBookShopAPP.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutsController {

    @GetMapping("/about")
    public String getAboutPage(){
        return "about";
    }
}
