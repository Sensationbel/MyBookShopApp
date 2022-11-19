package com.example.MyBookShopAPP.repositories.jpa_repositories;

import com.example.MyBookShopAPP.model.book.rate.RateBooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RateBooksRepository extends JpaRepository<RateBooksEntity, Integer> {

    RateBooksEntity save(RateBooksEntity rateBooksEntity);

    @Query(value = "SELECT * FROM Rate_book r WHERE r.book_id=?1", nativeQuery = true)
    RateBooksEntity findByBookId(Integer bookId);
}
