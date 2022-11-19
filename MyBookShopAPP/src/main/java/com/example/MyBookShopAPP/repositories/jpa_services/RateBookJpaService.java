package com.example.MyBookShopAPP.repositories.jpa_services;

import com.example.MyBookShopAPP.model.book.rate.RateBooksEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.RateBooksInterface;
import com.example.MyBookShopAPP.repositories.jpa_repositories.RateBooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateBookJpaService implements RateBooksInterface {

    private final RateBooksRepository rateRepository;

    @Override
    public RateBooksEntity save(RateBooksEntity rateBooksEntity) {
        return rateRepository.save(rateBooksEntity);
    }

    @Override
    public RateBooksEntity findByBookId(Integer bookId) {
        return rateRepository.findByBookId(bookId);
    }
}
