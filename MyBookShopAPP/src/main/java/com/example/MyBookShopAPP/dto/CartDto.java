package com.example.MyBookShopAPP.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDto {

    private Integer totalPrice;
    private Integer totalOldPrice;
    private List<BooksDto> books;

    public CartDto(List<BooksDto> books) {
        this.books = books;
        this.totalPrice = getTotalPrice(books);
        this.totalOldPrice = getTotalOldPrice(books);
    }

    private static Integer getTotalOldPrice(List<BooksDto> books) {
        return books.stream().map(BooksDto::getPrice).reduce(Integer::sum).orElse(0);
    }

    private Integer getTotalPrice(List<BooksDto> books) {
        return books.stream().map(BooksDto::getDiscountPrice).reduce(Integer::sum).orElse(0);
    }
}
