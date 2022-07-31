package org.example.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class Book {

    private Integer id;
    private String author;
    private String title;
    @NotNull
    @Digits(integer = 4, fraction = 0)
    private Integer size;
}
