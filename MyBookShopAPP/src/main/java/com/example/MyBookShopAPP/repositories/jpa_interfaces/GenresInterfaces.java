package com.example.MyBookShopAPP.repositories.jpa_interfaces;

import com.example.MyBookShopAPP.model.genre.GenreEntity;

import java.util.List;

public interface GenresInterfaces {

    List<GenreEntity> findAllByParentIdIsNull();
    List<GenreEntity> findAllByParentId(int id);
}
