package com.example.MyBookShopAPP.model;

import com.example.MyBookShopAPP.model.book.file.BookFileEntity;
import com.example.MyBookShopAPP.model.book.review.BookReviewEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "authors")
@ApiModel("data model of author entity")
public class AuthorsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "author id generated by db", position = 1)
    private Integer id;
    @Column(columnDefinition = "VARCHAR(255)")
    private String photo;
    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String slug;
    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private List<BooksEntity> books;

}
