package com.example.MyBookShopAPP.repositories.jpa_interfaces;

import com.example.MyBookShopAPP.model.user.UserContactEntity;

public interface UserContactInterfaces {
    UserContactEntity findByContact(String contact);
    void save(UserContactEntity userContact);
}
