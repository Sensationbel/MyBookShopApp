package com.example.MyBookShopAPP.model.book.rate;

import com.example.MyBookShopAPP.model.BooksEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="rate_book")
@Getter
@Setter
public class RateBooksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="rating_star_one")
    private int ratingStarOne;
    @Column(name="rating_star_two")
    private int ratingStarTwo;
    @Column(name="rating_star_three")
    private int ratingStarThree;
    @Column(name="rating_star_four")
    private int ratingStarFour;
    @Column(name="rating_star_five")
    private int ratingStarFive;

    @OneToOne()
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BooksEntity rateBooks;

}
