package com.example.MyBookShopAPP.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthorSlugDto {

    private int id;
    private String name;
    private String photo;
    private String description;
    private String slug;

    private List<BooksDto> books;


}
