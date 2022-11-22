package com.example.MyBookShopAPP.dto.reviewes;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReviewsResult {

    private int totalCountReviews;
    private List<ReviewDto> reviewDtoList;

    public ReviewsResult(List<ReviewDto> reviewDtoList){
        this.reviewDtoList = reviewDtoList;
        this.totalCountReviews = reviewDtoList.size();
    }
}
