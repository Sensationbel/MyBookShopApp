package com.example.MyBookShopAPP.repositories.jpa_services;

import com.example.MyBookShopAPP.model.AuthorsEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.AuthorsInterfaces;
import com.example.MyBookShopAPP.repositories.jpa_repositories.AuthorsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorsJpaServices implements AuthorsInterfaces {

    private final AuthorsRepository ar;
    @Override
    public List<AuthorsEntity> findALL() {
        return ar.findAll();
    }
}
