package org.example.app.services;

import org.example.app.repository.BookRepository;
import org.example.app.repository.ProjectRepository;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBookId(Integer bookIdRemove) {
        return bookRepo.removeItemById(bookIdRemove);
    }

    public boolean checkBookOfEmpty(Book book) {
        return !book.getAuthor().isEmpty() ||
                !book.getTitle().isEmpty() ||
                book.getSize() != null;
    }

    public boolean removeBookRegex(String regex) {
        String[] request = regex.split("\s+");

        return switch (request[0]) {
            case ("author") -> bookRepo.removeItemByAuthor(request[1]);
            case ("title") -> bookRepo.removeItemByTitle(request[1]);
            case ("size") -> bookRepo.removeItemBySize(request[1]);
            default -> false;
        };


    }
}
