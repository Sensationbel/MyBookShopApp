package com.example.MyBookShopAPP.repositories.jpa_services;

import com.example.MyBookShopAPP.model.book.file.BookFileEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.BookFileInterfaces;
import com.example.MyBookShopAPP.repositories.jpa_repositories.BookFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookFileJpaServices implements BookFileInterfaces {

    private final BookFileRepository bf;

    @Override
    public BookFileEntity findBookFileEntitiesByHash(String hash) {
        return bf.findBookFileEntitiesByHash(hash);
    }
}
