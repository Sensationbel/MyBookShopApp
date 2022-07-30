package org.example.app.repository;

import lombok.extern.log4j.Log4j;
import org.example.app.services.IdProvider;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(context.getBean(IdProvider.class).provideId(book));
        log.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(String bookIdRemove) {
        for(Book book : retreiveAll()){
            if(book.getId().equals(bookIdRemove)){
                log.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public boolean removeItemByAuthor(String author) {
        boolean result = false;
        for (Book book : retreiveAll()) {
            if(book.getAuthor().equals(author)){
                log.info("remove book completed: " + book);
                result = repo.remove(book);
            }
        }
        return result;
    }

    @Override
    public boolean removeItemByTitle(String title) {
        boolean result = false;
        for (Book book : retreiveAll()) {
            if(book.getTitle().equals(title)){
                log.info("remove book completed: " + book);
                result = repo.remove(book);
            }
        }
        return result;
    }

    @Override
    public boolean removeItemBySize(String size) {
        boolean result = false;
        for (Book book : retreiveAll()) {
            if(book.getSize() == Integer.valueOf(size)){
                log.info("remove book completed: " + book);
                result = repo.remove(book);
            }
        }
        return result;
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
