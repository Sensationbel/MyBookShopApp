package com.example.MyBookShopAPP.repositories.jpa_services;

import com.example.MyBookShopAPP.model.user.UserContactEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.UserContactInterfaces;
import com.example.MyBookShopAPP.repositories.jpa_repositories.UserContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserContactJpaServices implements UserContactInterfaces {

    private final UserContactRepository ucr;
    @Override
    public UserContactEntity findByContact(String contact) {
        return ucr.findByContact(contact);
    }

    @Override
    public void save(UserContactEntity userContact) {
        ucr.save(userContact);
    }


}
