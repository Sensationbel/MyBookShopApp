package com.example.MyBookShopAPP.repositories.jpa_interfaces;

import com.example.MyBookShopAPP.model.BooksEntity;

import java.util.List;

public interface BooksInterfaces {

    List<BooksEntity> findALL();
}
