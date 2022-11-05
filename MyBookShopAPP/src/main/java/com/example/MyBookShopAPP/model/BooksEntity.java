package com.example.MyBookShopAPP.model;

import com.example.MyBookShopAPP.model.book.file.BookFileEntity;
import com.example.MyBookShopAPP.model.genre.GenreEntity;
import com.example.MyBookShopAPP.model.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "books")
public class BooksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pub_date",columnDefinition = "TIMESTAMP", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date pubDate;

    @Column(name = "is_bestseller", columnDefinition = "SMALLINT", nullable = false)
    private short isBestseller;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String slug;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String title;

    @Column(columnDefinition = "VARCHAR(255)")
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column( columnDefinition = "INT", nullable = false)
    private int price;

    @Column(columnDefinition = "FLOAT DEFAULT 0", nullable = false)
    private float discount;
    @Column(name = "count_bought")
    private int countBought;

    @Column(name = "count_put_basket")
    private int countPutBasket;

    @Column(name = "count_postponed")
    private int countPostponed;

    @OneToMany(mappedBy = "book")
    private List<BookFileEntity> bookFileList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="book2author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
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
