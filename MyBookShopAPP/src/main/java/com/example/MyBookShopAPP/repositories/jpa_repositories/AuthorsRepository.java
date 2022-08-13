package com.example.MyBookShopAPP.repositories.jpa_repositories;

import com.example.MyBookShopAPP.data.Authors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorsRepository extends JpaRepository<Authors, Integer> {
}
