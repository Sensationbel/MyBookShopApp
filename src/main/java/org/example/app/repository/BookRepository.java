package org.example.app.repository;

import lombok.extern.log4j.Log4j;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

//    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Book> retreiveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());

        jdbcTemplate.update("INSERT INTO books(author,title,size) VALUES(:author,:title,:size)", parameterSource);
//        book.setId(context.getBean(IdProvider.class).provideId(book));
        log.info("store new book: " + book);
//        repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", bookIdRemove);
        jdbcTemplate.update("DELETE FROM books WHERE id=:id", parameterSource);
        log.info("remove book by id completed");
        return true;
    }

    @Override
    public boolean removeItemByAuthor(String author) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", author);
        jdbcTemplate.update("DELETE FROM books WHERE author=:author", parameterSource);
        log.info("remove book by author completed");
        return true;
    }

    @Override
    public boolean removeItemByTitle(String title) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource() ;
        parameterSource.addValue("title", title);
        jdbcTemplate.update("DELETE FROM books WHERE title=:title", parameterSource);
        log.info("remove book by title completed");
        return true;
    }

    @Override
    public boolean removeItemBySize(String size) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("size", size);
        jdbcTemplate.update("DELETE FROM books WHERE size=:size", parameterSource);
        log.info("remove book by size completed");
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private void defaultInit() {
        log.info("default INIT in book repo bean");
    }

    private void defaultDestroy() {
        log.info("default DESTROY in book repo bean");
    }
}
