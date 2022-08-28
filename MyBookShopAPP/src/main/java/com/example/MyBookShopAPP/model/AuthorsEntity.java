package com.example.MyBookShopAPP.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "author")
public class AuthorsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "VARCHAR(255)")
    private String photo;
    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String slug;
    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="book2author",
    joinColumns = @JoinColumn(name = "author_id"),
    inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<BooksEntity> books;

}
