package com.example.MyBookShopAPP.model.enums;

import lombok.Getter;

@Getter
public enum ContactType {

    PHONE("PHONE"),
    EMAIL("EMAIL");

    private final String type;

    ContactType (String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
