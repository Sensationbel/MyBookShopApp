package com.example.MyBookShopAPP.repositories.jpa_interfaces;

import com.example.MyBookShopAPP.model.book.rate.RateBooksEntity;

public interface RateBooksInterface {

    RateBooksEntity save(RateBooksEntity rateBooksEntity);
    RateBooksEntity findByBookId(Integer bookId);
}
