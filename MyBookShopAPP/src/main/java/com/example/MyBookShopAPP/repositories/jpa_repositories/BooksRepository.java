package com.example.MyBookShopAPP.repositories.jpa_repositories;

import com.example.MyBookShopAPP.model.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<BooksEntity, Integer> {
}
