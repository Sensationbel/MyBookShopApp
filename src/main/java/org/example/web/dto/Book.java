package org.example.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Book {

    private String id;
    private String author;
    private String title;
    private Integer size;
}
