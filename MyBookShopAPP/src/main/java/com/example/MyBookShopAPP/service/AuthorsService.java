package com.example.MyBookShopAPP.service;

import com.example.MyBookShopAPP.dto.AuthorSlugDto;
import com.example.MyBookShopAPP.dto.AuthorsDto;
import com.example.MyBookShopAPP.dto.BooksDto;
import com.example.MyBookShopAPP.model.AuthorsEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.AuthorsInterface;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.BooksInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorsService {

    private final AuthorsInterface ai;
    private final BooksInterface bi;
    private final BookService bookService;

    public Map<String, List<AuthorsDto>> getAuthorsMap() {
        List<AuthorsDto> authors = new ArrayList<>();
        ai.findALL().forEach(author ->{
            AuthorsDto authorsDto = createAuthorsDto(author);
            authors.add(authorsDto);
        });
        return authors
                .stream()
                .collect(Collectors
                        .groupingBy((AuthorsDto a) -> {return a.getLastName().substring(0, 1);}));
    }

    private  AuthorsDto createAuthorsDto(AuthorsEntity author) {
        AuthorsDto authorsDto = new AuthorsDto();
        authorsDto.setAuthorId(author.getId());
        String[] name = author.getName().split(" ");
        authorsDto.setFirstName(name[0]);
        authorsDto.setLastName(name[1]);
        authorsDto.setSlug(author.getSlug());
        return authorsDto;
    }

    public AuthorsDto getAuthorsDtoById(int authorId) {
        return createAuthorsDto(ai.findById(authorId));
    }

    public AuthorSlugDto getAuthorsSlugDtoById(String slug) {
        AuthorsEntity authors = ai.findBySlug(slug);
        AuthorSlugDto authorsDto = new AuthorSlugDto();
        authorsDto.setId(authors.getId());
        authorsDto.setSlug(authors.getSlug());
        authorsDto.setName(authors.getName());
        authorsDto.setPhoto(authors.getPhoto());
        authorsDto.setDescription(authors.getDescription());
        authorsDto.setBooks(bookService.getBooksDtoList(authors.getBooks()));
        return authorsDto;
    }

    public List<BooksDto> getBooksByAuthorId(int offset, int limit, int id) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookService.getBooksDtoList(bi.findAllByAuthors(id, nextPage));
    }

    public void exchangePhoto(String savePath, String slug) {
        AuthorsEntity author = ai.findBySlug(slug);
        author.setPhoto(savePath);
        ai.save(author);
    }
}
