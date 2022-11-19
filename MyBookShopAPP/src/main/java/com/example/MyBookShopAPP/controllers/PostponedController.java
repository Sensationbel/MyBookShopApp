package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.CartDto;
import com.example.MyBookShopAPP.dto.SearchWordDto;
import com.example.MyBookShopAPP.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringJoiner;

@Log4j2
@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class PostponedController {

    private final BookService bookService;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }
    @ModelAttribute(name = "postponedBook")
    public CartDto bookCart(){
        return new CartDto(Collections.emptyList());
    }

    @GetMapping("/postponed")
    public String postponedPage(@CookieValue(name = "postponedContents", required = false) String postponedContents,
                                Model model) {
        if (postponedContents == null || postponedContents.equals("")) {
            model.addAttribute("isPostponedEmpty", true);
        } else{
            model.addAttribute("isPostponedEmpty", false);
            model.addAttribute("postponedBook", bookService.getBookCart(postponedContents));
        }
        return "postponed";
    }

    @PostMapping("/changeBookStatus/postponed/{slug}")
    public String getPostponedBooksPage(@PathVariable("slug") String slug,
                                        @CookieValue(value = "postponedContents", required = false) String postponedContents,
                                        HttpServletResponse response, Model model) {

        if (postponedContents == null || postponedContents.equals("")) {
            Cookie cookie = new Cookie("postponedContents", slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isPostponeEmpty", false);
            log.info("book by " + slug + " was putted in postponed");
        } else if (!postponedContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(postponedContents).add(slug);
            Cookie cookie = new Cookie("postponedContents", stringJoiner.toString());
            cookie.setPath("/books");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            model.addAttribute("isPostponedEmpty", false);
            log.info("book by " + slug + " was putted in postponed");
        }
        log.info("postponed contains: " + postponedContents);

        return "redirect:/books/" + slug;
    }

    @PostMapping("/changeBookStatus/postponed/remove/{slug}")
    public String handleRemoveFromPostponedRequest(@PathVariable("slug") String slug,
                                                   @CookieValue(name = "postponedContents",
                                                           required = false) String postponedContents,
                                                   HttpServletResponse response,
                                                   Model model){
        if(postponedContents != null || !postponedContents.equals("")){
            removeBooksOfCookie(slug, postponedContents, response, model);
            log.info("book by slug = " + slug + " of postponed was removed");
        }else{
            model.addAttribute("isPostponedEmpty", true);
            log.info("basket postponed is empty");
        }
        return "redirect:/books/postponed";
    }

    @PostMapping("/changeBookStatus/postponed/cart/{slug}")
    public ModelAndView moveBookFromPostponedToCart(@PathVariable("slug") String slug,
                                                    @CookieValue(name = "postponedContents",
                                                      required = false) String postponedContents,
                                                    HttpServletResponse response,
                                                    Model model){

        if(postponedContents != null || !postponedContents.equals("")){
            removeBooksOfCookie(slug, postponedContents, response, model);
            log.info("book by slug = " + slug + " moved to cart");
        } else{
            model.addAttribute("isPostponedEmpty", true);
            log.info("basket postponed is empty");
        }
        return new ModelAndView("forward:/books/changeBookStatus/" + slug);
    }

    private void removeBooksOfCookie(@PathVariable("slug") String slug, @CookieValue(name = "postponedContents", required = false) String postponedContents, HttpServletResponse response, Model model) {
        ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(postponedContents.split("/")));
        cookieBooks.remove(slug);
        Cookie cookie = new Cookie("postponedContents", String.join("/", cookieBooks));
        cookie.setPath("/books");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        model.addAttribute("isPostponedEmpty", false);
    }
}
