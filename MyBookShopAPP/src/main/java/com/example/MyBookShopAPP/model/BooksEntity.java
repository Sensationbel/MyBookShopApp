package com.example.MyBookShopAPP.model;

import com.example.MyBookShopAPP.model.genre.GenreEntity;
import com.example.MyBookShopAPP.model.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "book")
public class BooksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime pubDate;

    @Column(columnDefinition = "SMALLINT", nullable = false)
    private short isBestseller;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String slug;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String title;

    @Column(columnDefinition = "VARCHAR(255)")
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "INT", nullable = false)
    private int price;

    @Column(columnDefinition = "SMALLINT DEFAULT 0", nullable = false)
    private short discount;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "books")
    private Set<AuthorsEntity> authors;

    @ManyToMany(mappedBy = "books")
    private Set<GenreEntity> genres;

    @ManyToMany(mappedBy = "booksUsers")
    private Set<UserEntity> user2books;

    @ManyToMany(mappedBy = "booksDownload")
    private Set<UserEntity> userDownload;

    @ManyToMany(mappedBy = "balanceTransaction")
    private Set<UserEntity> userTransaction;

    @ManyToMany(mappedBy = "reviews")
    private Set<UserEntity> usersReview;
}
