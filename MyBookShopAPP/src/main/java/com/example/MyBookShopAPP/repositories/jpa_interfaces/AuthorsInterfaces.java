package com.example.MyBookShopAPP.repositories.jpa_interfaces;

import com.example.MyBookShopAPP.model.AuthorsEntity;

import java.util.List;

public interface AuthorsInterfaces {

    List<AuthorsEntity> findALL();
    AuthorsEntity findById(int id);
    AuthorsEntity findBySlug(String slug);
    AuthorsEntity save(AuthorsEntity author);
}
