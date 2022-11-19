package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.BooksDto;
import com.example.MyBookShopAPP.dto.CartDto;
import com.example.MyBookShopAPP.dto.SearchWordDto;
import com.example.MyBookShopAPP.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Log4j2
@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookshopCartController {

    private final BookService bookService;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute(name = "bookCart")
    public CartDto bookCart(){
        return new CartDto(Collections.emptyList());
    }

    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model){
       if(cartContents ==null || cartContents.equals("")){
           model.addAttribute("isCartEmpty", true);
       } else {
           model.addAttribute("isCartEmpty", false);
           model.addAttribute("bookCart", bookService.getBookCart(cartContents));
       }
       return "cart";
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveFromCartRequest(@PathVariable("slug") String slug,
                                              @CookieValue(name = "cartContents", required = false) String cartContents,
                                              HttpServletResponse response,
                                              Model model){
        if(cartContents != null && !cartContents.equals("")){
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
            log.info("book by slug = " + slug + "of cart was removed");
        } else{
            model.addAttribute("isCartEmpty",     true);
            log.info("basket is empty");
        }
        log.info("cart contents is contained = " + cartContents);
        return "redirect:/books/cart";
    }

    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug,
                                         @CookieValue(name = "cartContents",
    required = false) String cartContents, HttpServletResponse response, Model model){

        if(cartContents == null || cartContents.equals("")){
            Cookie cookie = new Cookie("cartContents", slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
            log.info("book by " + slug + " was putted in basket");
        }else if(!cartContents.contains(slug)){
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
            log.info("book by " + slug + " was putted in basket");
        }
        return "redirect:/books/" + slug;
    }
}
