package com.example.MyBookShopAPP.repositories.jpa_services;

import com.example.MyBookShopAPP.model.BooksEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.BooksInterfaces;
import com.example.MyBookShopAPP.repositories.jpa_repositories.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksJpaServices implements BooksInterfaces {

    private final BooksRepository br;

    @Override
    public List<BooksEntity> findALL() {
        return br.findAll();
    }

    @Override
    public Page<BooksEntity> findALL(Pageable pageable) {
        return br.findAll(pageable);
    }

    @Override
    public Page<BooksEntity> findBooksEntitiesByTitleContaining(String title, Pageable pageable) {
        return br.findBooksEntitiesByTitleContaining(title, pageable);
    }

    @Override
    public Page<BooksEntity> findAllByOrderByPubDate(Pageable pageable) {
        return br.findByOrderByPubDate(pageable);
    }

    @Override
    public Page<BooksEntity> findAllByPubDateBetweenOrderByPubDate(Date pubDate,
                                                                   Date pubDate2,
                                                                   Pageable pageable) {
        return br.findAllByPubDateBetweenOrderByPubDate(pubDate, pubDate2, pageable);
    }

    @Override
    public Page<BooksEntity> getAllByAvg(Pageable pageable) {
        return br.getAllByAvg(pageable);
    }

    public List<BooksEntity> getBooksEntitiesByGenres(Pageable nextPage, String slug){
        return br.getBooksEntitiesByGenres(nextPage, slug);
    }

    @Override
    public List<BooksEntity> findAllByAuthors(int id, Pageable pageable) {
        return br.findAllByAuthors(pageable, id);
    }

}
