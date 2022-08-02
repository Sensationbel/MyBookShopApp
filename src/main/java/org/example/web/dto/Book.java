package org.example.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class Book {

    private Integer id;
    @NotEmpty(message = "field author value must be string")
    private String author;
    @NotEmpty(message = "field title value must be string")
    private String title;
    @NotNull(message = "field id value must be digit and less than 4 signs")
    @Digits(integer = 4, fraction = 0)
    private Integer size;
}
