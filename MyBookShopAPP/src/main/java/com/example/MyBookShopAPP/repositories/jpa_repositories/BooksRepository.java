package com.example.MyBookShopAPP.repositories.jpa_repositories;

import com.example.MyBookShopAPP.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Integer> {
}
