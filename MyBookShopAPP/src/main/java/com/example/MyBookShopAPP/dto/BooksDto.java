package com.example.MyBookShopAPP.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BooksDto {

    private int id;
    private String slug;
    private String title;
    private String image;
    private int price;
    private int discountPrice;
    private String authors;
    private short isBestseller;
    private short discount;
}
