package com.example.MyBookShopAPP.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BooksDto {

    private Integer id;
    private String title;
    private String priceOld;
    private String price;
    private String author;
}
