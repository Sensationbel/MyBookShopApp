package com.example.MyBookShopAPP.service;

import com.example.MyBookShopAPP.dto.reviewes.ReviewDto;
import com.example.MyBookShopAPP.dto.reviewes.ReviewsResult;
import com.example.MyBookShopAPP.model.BooksEntity;
import com.example.MyBookShopAPP.model.book.review.BookReviewEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.BooksInterface;
import com.example.MyBookShopAPP.repositories.jpa_repositories.LikeReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final BooksInterface bi;
    private final LikeReviewRepository lr;
    public ReviewsResult getUsersReviewByBooksSlug(String slug) {
        BooksEntity book = bi.findBySlug(slug);
        List<BookReviewEntity> reviewList = book.getBookReviewList();

        return new ReviewsResult(getReviewDtoOfReviewList(reviewList));
    }

    private List<ReviewDto> getReviewDtoOfReviewList(List<BookReviewEntity> reviewList) {

        return reviewList.stream().map(bookReviewEntity -> {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setAuthorsName(bookReviewEntity.getUsers().getName());
            reviewDto.setDatePub(bookReviewEntity.getTime());
            reviewDto.setBooksReview(bookReviewEntity.getText());
            reviewDto.setCountLikes(getLikeValueCount(bookReviewEntity, 1));
            reviewDto.setCountDislikes(getLikeValueCount(bookReviewEntity, -1));
            reviewDto.setAvgRateReview(countAvgRate(reviewDto));
            return reviewDto;
        }
            ).toList();
    }

    private Integer countAvgRate(ReviewDto reviewDto) {
        int difference = reviewDto.getCountLikes() - reviewDto.getCountDislikes();
        int totalRate = reviewDto.getCountLikes() + reviewDto.getCountDislikes();
        if(difference <= 0){
            return 0;
        } else  return Math.toIntExact(Math.round((double)difference * 5 / totalRate));
    }

    private Integer getLikeValueCount(BookReviewEntity bookReviewEntity, int value) {
        Integer likeCount = lr.getBookReviewLikeValue(bookReviewEntity.getId(), value);
        if(likeCount== null){
            return 0;
        } else return likeCount;
    }
}
