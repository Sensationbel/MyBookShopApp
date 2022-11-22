package com.example.MyBookShopAPP.service;

import com.example.MyBookShopAPP.dto.ChangeRateResult;
import com.example.MyBookShopAPP.dto.RateDto;
import com.example.MyBookShopAPP.model.BooksEntity;
import com.example.MyBookShopAPP.model.book.rate.RateBooksEntity;
import com.example.MyBookShopAPP.model.user.UserEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.BooksInterface;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.RateBooksInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class RateBooksService {

    private final RateBooksInterface ri;
    private final BooksInterface bi;

    public ChangeRateResult getResultChangedRateBook(String slug, Integer value) {

        BooksEntity book = bi.findBySlug(slug);
        RateBooksEntity rateBooks = ri.findByBookId(book.getId());
        if(rateBooks != null) {
            setRateBooksStar(rateBooks, value);
        } else {
            rateBooks = new RateBooksEntity();
            rateBooks.setRateBooks(book);
            setRateBooksStar(rateBooks, value);
        }
        ri.save(rateBooks);
        return new ChangeRateResult();
    }

    private void setRateBooksStar(RateBooksEntity rateBooks, int value) {
        switch (value){
            case (1) -> rateBooks.setRatingStarOne(rateBooks.getRatingStarOne() + 1);
            case (2) -> rateBooks.setRatingStarTwo(rateBooks.getRatingStarTwo() + 1);
            case (3) -> rateBooks.setRatingStarThree(rateBooks.getRatingStarThree() + 1);
            case (4) -> rateBooks.setRatingStarFour(rateBooks.getRatingStarFour() + 1);
            case (5) -> rateBooks.setRatingStarFive(rateBooks.getRatingStarFive() + 1);
        }
    }

    public RateDto getStatisticRate(String slug) {
        RateDto stat = new RateDto();
        BooksEntity book = bi.findBySlug(slug);
        RateBooksEntity rateBooks = ri.findByBookId(book.getId());
        if(rateBooks == null) {
            rateBooks = new RateBooksEntity();
            rateBooks.setRateBooks(book);
        }
        stat.setTotalRate(getTotalRateOfRateBooks(rateBooks));
        addRateForEachStars(stat,rateBooks);
        getAddAvgRate(stat, rateBooks);
        stat.addAllCountStars();
        return stat;
    }

    private void getAddAvgRate(RateDto stat, RateBooksEntity rateBooks) {
        int totalStars = rateBooks.getRatingStarOne() +
                (rateBooks.getRatingStarTwo() * 2) +
                (rateBooks.getRatingStarThree() * 3) +
                (rateBooks.getRatingStarFour() * 4) +
                (rateBooks.getRatingStarFive() * 5);
        stat.setAvgRate(Math.toIntExact(Math.round((double) totalStars / stat.getTotalRate())));
    }

    private void addRateForEachStars(RateDto stat, RateBooksEntity rateBooks) {
        stat.setCountStarsOne(rateBooks.getRatingStarOne());
        stat.setCountStarsTwo(rateBooks.getRatingStarTwo());
        stat.setCountStarsThree(rateBooks.getRatingStarThree());
        stat.setCountStarsFour(rateBooks.getRatingStarFour());
        stat.setCountStarsFive(rateBooks.getRatingStarFive());
    }

    private int getTotalRateOfRateBooks(RateBooksEntity rateBooks) {
        return rateBooks.getRatingStarOne()
                + rateBooks.getRatingStarTwo()
                + rateBooks.getRatingStarThree()
                + rateBooks.getRatingStarFour()
                + rateBooks.getRatingStarFive();
    }


}
