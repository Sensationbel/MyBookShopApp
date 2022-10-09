package com.example.MyBookShopAPP.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class GenresDto {

    private int id;
    private Integer parentId;
    private String genresName;
    private int booksCount;

    private List<GenresDto> childList = new ArrayList<>();

}
