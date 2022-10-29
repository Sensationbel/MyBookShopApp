package com.example.MyBookShopAPP.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorsDto {

    private int authorId;
    private String firstName;
    private String lastName;
    private String slug;
}
