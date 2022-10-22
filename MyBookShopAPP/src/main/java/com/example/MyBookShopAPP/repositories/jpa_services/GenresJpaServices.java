package com.example.MyBookShopAPP.repositories.jpa_services;

import com.example.MyBookShopAPP.model.genre.GenreEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.GenresInterfaces;
import com.example.MyBookShopAPP.repositories.jpa_repositories.GenresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenresJpaServices implements GenresInterfaces {

    private final GenresRepository gr;

    @Override
    public List<GenreEntity> findAllByParentIdIsNull() {
        return gr.findAllByParentIdIsNull();
    }

    @Override
    public List<GenreEntity> findAllByParentId(int id) {
        return gr.findAllByParentId(id);
    }

    @Override
    public GenreEntity findBySlug(String slug) {
        return gr.findBySlug(slug);
    }

    @Override
    public GenreEntity findById(int id) {
        return gr.findById(id);
    }

    @Override
    public List<GenreEntity> getGenresBySlug(String slug) {
        return gr.getGenresBySlug(slug);
    }

    @Override
    public List<GenreEntity> findAll() {
        return gr.findAll();
    }
}
