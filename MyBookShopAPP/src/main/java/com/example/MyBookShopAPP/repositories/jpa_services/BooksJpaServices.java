package com.example.MyBookShopAPP.repositories.jpa_services;

import com.example.MyBookShopAPP.data.Book;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.BooksInterfaces;
import com.example.MyBookShopAPP.repositories.jpa_repositories.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksJpaServices implements BooksInterfaces {

    private final BooksRepository br;

    @Override
    public List<Book> findALL() {
        return br.findAll();
    }
}