package com.example.MyBookShopAPP.repositories.jpa_repositories;

import com.example.MyBookShopAPP.model.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenresRepository extends JpaRepository<GenreEntity, Integer> {

    List<GenreEntity> findAllByParentIdIsNull();

    List<GenreEntity> findAllByParentId(int id);
}
