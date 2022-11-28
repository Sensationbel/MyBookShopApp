package com.example.MyBookShopAPP.repositories.jpa_services;

import com.example.MyBookShopAPP.model.user.UserEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.UsersInterfaces;
import com.example.MyBookShopAPP.repositories.jpa_repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersJpaServices implements UsersInterfaces {

    private final UserRepository ur;

    @Override
    public UserEntity save(UserEntity user) {
        return ur.save(user);
    }
}
