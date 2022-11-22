package com.example.MyBookShopAPP.repositories.jpa_repositories;

import com.example.MyBookShopAPP.model.book.review.BookReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeReviewRepository extends JpaRepository<BookReviewLikeEntity, Integer> {

    @Query(value="SELECT count(r.value) FROM Book_review_like r WHERE r.review_id=?1 AND r.value=?2", nativeQuery = true)
    Integer getBookReviewLikeValue(int reviewId, int value);
}
