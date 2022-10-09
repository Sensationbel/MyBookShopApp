package com.example.MyBookShopAPP.repositories.jpa_repositories;

import com.example.MyBookShopAPP.model.BooksEntity;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface BooksRepository extends JpaRepository<BooksEntity, Integer> {

    Page<BooksEntity> findBooksEntitiesByTitleContaining(String title,
                                                         Pageable pageable);

    Page<BooksEntity> findByOrderByPubDate(Pageable pageable);

    Page<BooksEntity> findAllByPubDateBetweenOrderByPubDate(Date pubDate,
                                                            Date pubDate2,
                                                            Pageable pageable);
    @Query(value = "SELECT *, (b.count_bought + 0.7*b.count_put_basket + 0.3*b.count_postponed)" +
            " AS summa FROM Books b ORDER by summa  DESC", nativeQuery = true)
    Page<BooksEntity> getAllByAvg(Pageable pageable);
}
