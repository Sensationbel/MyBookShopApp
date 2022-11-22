package com.example.MyBookShopAPP.dto;

import com.example.MyBookShopAPP.model.BooksEntity;
import com.example.MyBookShopAPP.model.book.file.BookFileEntity;
import com.example.MyBookShopAPP.model.genre.GenreEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class SlugBookDto {

    private Integer bookId;
    private String slug;
    private String title;
    private String image;
    private String author;
    private String description;
    private int discountPrice;
    private int price;
    private boolean isBestseller;

    private Map<String, String> tags;
    private List<BookFileEntity> bookFileList = new ArrayList<>();

    public void addTags(Set<GenreEntity> genres) {
        tags = genres.stream().collect(Collectors.toMap(GenreEntity::getSlug, GenreEntity::getName));
    }

    public void addBookFileList(BooksEntity book) {
        bookFileList.addAll(book.getBookFileList());
    }

    public void isBestseller(Short value) {
        isBestseller = value == 1;
    }
}
