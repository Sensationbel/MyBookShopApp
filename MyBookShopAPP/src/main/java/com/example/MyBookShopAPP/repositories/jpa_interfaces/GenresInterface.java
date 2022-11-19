package com.example.MyBookShopAPP.repositories.jpa_interfaces;

import com.example.MyBookShopAPP.model.genre.GenreEntity;

import java.util.List;

public interface GenresInterface {

    List<GenreEntity> findAllByParentIdIsNull();
    List<GenreEntity> findAllByParentId(int id);
    GenreEntity findBySlug(String slug);
    GenreEntity findById(int id);
    List<GenreEntity> getGenresBySlug(String slug);
    List<GenreEntity> findAll();
}
