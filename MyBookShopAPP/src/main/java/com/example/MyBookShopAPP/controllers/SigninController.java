package com.example.MyBookShopAPP.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SigninController {

    @GetMapping("/signin")
    public String signinPage(){
        return "signin";
    }
}