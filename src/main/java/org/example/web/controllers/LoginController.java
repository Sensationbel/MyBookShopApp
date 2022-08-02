package org.example.web.controllers;

import lombok.extern.log4j.Log4j;
import org.example.app.exeptions.BookShelfException;
import org.example.app.services.LoginService;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j
@RequestMapping(value = "login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String login(Model model){
        log.info("GET /login returns login_page.html");
        model.addAttribute("loginForm", new LoginForm());
        return "login_page";
    }

    @PostMapping("/auth")
    public String authenticate(LoginForm loginForm) throws BookShelfException {
        if(loginService.authenticate(loginForm)){
            log.info("login ok redirect to book shelf");
            return "redirect:/books/shelf";
        } else {
            log.info("login FAIL redirect to login");
            throw new BookShelfException("invalid username or password");
        }
    }

    @ExceptionHandler(BookShelfException.class)
    public String handleError(Model model, BookShelfException exception){
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/404";
    }
}
