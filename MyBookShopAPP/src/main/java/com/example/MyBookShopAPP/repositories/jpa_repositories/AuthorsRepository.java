package com.example.MyBookShopAPP.repositories.jpa_repositories;

import com.example.MyBookShopAPP.model.AuthorsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorsRepository extends JpaRepository<AuthorsEntity, Integer> {

    AuthorsEntity findById(int id);
}
