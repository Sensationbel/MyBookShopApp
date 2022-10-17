package com.example.MyBookShopAPP.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BooksPageDto {

    private int count;
    private List<BooksDto> books;

    public BooksPageDto(List<BooksDto> booksDtoList) {
        this.books = booksDtoList;
        this.count = booksDtoList.size();
    }
}
