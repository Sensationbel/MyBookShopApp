package com.example.MyBookShopAPP.model.user;

import com.example.MyBookShopAPP.model.BooksEntity;
import com.example.MyBookShopAPP.model.book.review.BookReviewEntity;
import com.example.MyBookShopAPP.model.book.review.MessageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String hash;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime regTime;

    @Column(columnDefinition = "INT NOT NULL")
    private int balance;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    @ManyToMany
    @JoinTable(name = "book2user",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<BooksEntity> booksUsers;

    @ManyToMany
    @JoinTable(name = "file_download",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<BooksEntity> booksDownload;

    @ManyToMany
    @JoinTable(name = "balance_transaction",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<BooksEntity> balanceTransaction;

    @OneToMany(mappedBy = "users")
    private List<BookReviewEntity> reviewsList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Set<MessageEntity> messages;

}
