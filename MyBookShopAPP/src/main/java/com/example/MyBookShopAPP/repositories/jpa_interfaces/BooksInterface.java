package com.example.MyBookShopAPP.repositories.jpa_interfaces;

import com.example.MyBookShopAPP.model.BooksEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface BooksInterface {

    Page<BooksEntity> findALL(Pageable pageable);
    Page<BooksEntity> findBooksEntitiesByTitleContaining(String title,
                                                         Pageable pageable);
    Page<BooksEntity> findAllByOrderByPubDate(Pageable pageable);
    Page<BooksEntity> findAllByPubDateBetweenOrderByPubDate(Date pubDate,
                                                            Date pubDate2,
                                                            Pageable pageable);
    Page<BooksEntity> getAllByAvg(Pageable pageable);
    List<BooksEntity> getBooksEntitiesByGenres(Pageable nextPage, String slug);
    List<BooksEntity> findAllByAuthors(int id, Pageable pageable);
    BooksEntity findBySlug(String slug);
    BooksEntity save(BooksEntity book);
    List<BooksEntity> findAllBySlug(String[] slugs);

    BooksEntity findById(Integer bookId);

}
