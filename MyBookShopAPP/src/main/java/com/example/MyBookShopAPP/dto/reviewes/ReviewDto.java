package com.example.MyBookShopAPP.dto.reviewes;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto {

    private String authorsName;
    private LocalDateTime datePub;
    private String booksReview;
    private int countLikes;
    private int countDislikes;
    private int avgRateReview;
}
