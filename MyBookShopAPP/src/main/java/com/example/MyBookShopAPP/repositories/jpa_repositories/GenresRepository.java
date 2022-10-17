package com.example.MyBookShopAPP.repositories.jpa_repositories;

import com.example.MyBookShopAPP.model.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenresRepository extends JpaRepository<GenreEntity, Integer> {

    List<GenreEntity> findAllByParentIdIsNull();
    List<GenreEntity> findAllByParentId(int id);
    GenreEntity findBySlug(String slug);
    GenreEntity findById(int id);

    @Query(value = "WITH RECURSIVE generation AS (" +
            "SELECT gs.id, gs.name, gs.parent_id, gs.slug, 0 AS generation_number " +
            "FROM Genre gs " +
            "WHERE gs.slug=?1 " +
            "UNION ALL " +
            "SELECT child.id, child.name, child.parent_id, child.slug, generation_number+1 AS generation_number " +
            "FROM Genre child JOIN generation g ON g.id = child.parent_id) " +
            "SELECT g.id, g.name, g.parent_id, g.slug FROM generation g " +
            "LEFT JOIN Genre parent ON parent.id=g.parent_id " +
            "ORDER BY generation_number", nativeQuery = true)
    List<GenreEntity> getGenresBySlug(String slug);
}
