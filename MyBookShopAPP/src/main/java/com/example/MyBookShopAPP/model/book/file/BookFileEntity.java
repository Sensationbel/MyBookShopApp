package com.example.MyBookShopAPP.model.book.file;

import com.example.MyBookShopAPP.model.BooksEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book_file")
@Getter
@Setter
public class BookFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String hash;
    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String path;
    @Column(name = "type_id",nullable = false)
    private int typeId;
    @ManyToOne
    @JoinColumn(name = "book_id",referencedColumnName = "id")
    private BooksEntity book;

    public String getFileTypeExtension() {
        return BooksFileType.getFileExtensionString(typeId);
    }
}
