package com.example.MyBookShopAPP.repositories.jpa_interfaces;

import com.example.MyBookShopAPP.model.book.file.BookFileEntity;

public interface BookFileInterfaces {
    BookFileEntity findBookFileEntitiesByHash(String hash);
}
