package com.example.MyBookShopAPP.service;

import com.example.MyBookShopAPP.data.Book;
import com.example.MyBookShopAPP.dto.BooksDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final JdbcTemplate jdbcTemplate;
    public List<BooksDto> getBooksData() {
        List<BooksDto> books = jdbcTemplate.query("SELECT b.*, a.last_name FROM BOOKS b \n" +
                "JOIN AUTHORS a ON a.id=b.author_id; ", (ResultSet rs, int rowNum) ->{
            BooksDto book = new BooksDto();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("last_name"));
            book.setTitle(rs.getString("title"));
            book.setPriceOld(rs.getString("price_Old"));
            book.setPrice(rs.getString("price"));
            return book;
        });
        return new ArrayList<>(books);
    }
}
