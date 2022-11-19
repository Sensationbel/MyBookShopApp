package com.example.MyBookShopAPP.repositories.jpa_interfaces;

import com.example.MyBookShopAPP.model.book.links.Book2AuthorEntity;

import java.util.List;

public interface Book2AuthorInterface {
    List<Book2AuthorEntity> findBook2AuthorEntityByBookId(int bookId);
}
