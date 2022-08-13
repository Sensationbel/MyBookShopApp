package com.example.MyBookShopAPP.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Book {

    private Integer id;
    private String title;
    private String price_Old;
    private String price;
    private Integer author_id;
}
