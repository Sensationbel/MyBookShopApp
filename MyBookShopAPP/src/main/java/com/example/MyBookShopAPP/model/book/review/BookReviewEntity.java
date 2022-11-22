package com.example.MyBookShopAPP.model.book.review;

import com.example.MyBookShopAPP.model.BooksEntity;
import com.example.MyBookShopAPP.model.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_review")
@Getter
@Setter
public class BookReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "book_id", nullable = false, insertable = false, updatable = false)
    private int bookId;

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private int userId;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime time;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    private BooksEntity books;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity users;
}
