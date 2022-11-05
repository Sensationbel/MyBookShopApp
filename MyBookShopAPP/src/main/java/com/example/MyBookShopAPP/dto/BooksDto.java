package com.example.MyBookShopAPP.dto;

import com.example.MyBookShopAPP.model.book.file.BookFileEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private List<BookFileEntity> bookFileList = new ArrayList<>();
}
