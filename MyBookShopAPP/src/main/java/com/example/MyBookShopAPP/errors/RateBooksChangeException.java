package com.example.MyBookShopAPP.errors;

public class RateBooksChangeException extends Exception{

    public RateBooksChangeException(){
        super("Неверный id книги");
    }
}
