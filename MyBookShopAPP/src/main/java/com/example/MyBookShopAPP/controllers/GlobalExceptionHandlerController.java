package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.errors.EmptySearchException;
import com.example.MyBookShopAPP.errors.RateBooksChangeException;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(EmptySearchException.class)
    public String handlerEmptySearchException(EmptySearchException e, RedirectAttributes redirectAttributes){
        log.warn(e.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("searchError", e);
        return "redirect:/";
    }

    @ExceptionHandler(RateBooksChangeException.class)
    public String handlerRateBooksChangeException(RateBooksChangeException e, RedirectAttributes redirect){
        log.warn(e.getMessage());
        redirect.addFlashAttribute( "result", false);
        redirect.addFlashAttribute("error", e.getMessage());
        return "redirect:/";
    }
}
