package com.example.MyBookShopAPP.repositories.jpa_services;

import com.example.MyBookShopAPP.model.book.links.Book2AuthorEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.Book2AuthorInterface;
import com.example.MyBookShopAPP.repositories.jpa_repositories.Book2AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Book2AuthorJpaServices implements Book2AuthorInterface {

    private final Book2AuthorRepository b2ARep;

    @Override
    public List<Book2AuthorEntity> findBook2AuthorEntityByBookId(int bookId) {
        return b2ARep.findBook2AuthorEntityByBookId(bookId);
    }
}
