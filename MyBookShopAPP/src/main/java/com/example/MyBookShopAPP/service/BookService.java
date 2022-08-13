package com.example.MyBookShopAPP.service;

import com.example.MyBookShopAPP.data.Book;
import com.example.MyBookShopAPP.dto.BooksDto;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.BooksInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BooksInterfaces bi;
    public List<BooksDto> getBooksData() {
        List<BooksDto> books = new ArrayList<>();
            bi.findALL().forEach(book ->{
            BooksDto booksDto = new BooksDto();
            booksDto.setAuthor(book.getAuthor().getLast_name());
            booksDto.setTitle(book.getTitle());
            booksDto.setPriceOld(book.getPrice_Old());
            booksDto.setPrice(book.getPrice());
            books.add(booksDto);
        });
        return books;
    }
}
