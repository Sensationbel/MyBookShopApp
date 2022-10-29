package com.example.MyBookShopAPP.service;

import com.example.MyBookShopAPP.dto.BooksDto;
import com.example.MyBookShopAPP.model.BooksEntity;
import com.example.MyBookShopAPP.model.book.links.Book2AuthorEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.AuthorsInterfaces;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.Book2AuthorInterfaces;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.BooksInterfaces;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BooksInterfaces bi;
    private final Book2AuthorInterfaces b2AuthInt;
    private final AuthorsInterfaces aServ;

    public List<BooksDto> getBooksDtoList(List<BooksEntity> list) {
        List<BooksDto> books = new ArrayList<>();
        list.forEach(book -> {
            BooksDto booksDto = createBooksDto(book);
            books.add(booksDto);
        });
        return books;
    }

    public BooksDto getBooksDtoBySlug(String slug) {
        return createBooksDto(bi.findBySlug(slug));
    }

    private BooksDto createBooksDto(BooksEntity book) {
        BooksDto booksDto = new BooksDto();
        booksDto.setId(book.getId());
        booksDto.setSlug(book.getSlug());
        booksDto.setImage(book.getImage());
        booksDto.setAuthors(getAuthors(book));
        booksDto.setTitle(book.getTitle());
        booksDto.setPrice(book.getPrice());
        booksDto.setDiscountPrice((int) getPriceOld(book));
        booksDto.setDiscount((short) (book.getDiscount()*100));
        booksDto.setIsBestseller(book.getIsBestseller());
        return booksDto;
    }

    private double getPriceOld(BooksEntity book) {
        return book.getDiscount() > 0.0 ? (book.getPrice() - (book.getPrice() * book.getDiscount())) : 0.0;
    }

    private String getAuthors(BooksEntity book) {
        List<Book2AuthorEntity> list = b2AuthInt.findBook2AuthorEntityByBookId(book.getId());
        Book2AuthorEntity b2a = getAuthor(list);
        String name = aServ.findById(b2a.getAuthorId()).getName();
        return list.size() > 1 ? (name + " And others...") : name;
    }

    private Book2AuthorEntity getAuthor(List<Book2AuthorEntity> list) {
        return list.stream().filter(b -> b.getSortIndex() == 1).findFirst().get();
    }

    public List<BooksDto> getPageOfRecommendedBook(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return getBooksDtoList(bi.findALL(nextPage).getContent());
    }

    public List<BooksDto> getPageOfSearchResultBooks(String searchWord, int offset, int limit){
        Pageable nextPage =  PageRequest.of(offset, limit);
        return getBooksDtoList(bi.findBooksEntitiesByTitleContaining(searchWord, nextPage).getContent());
    }

    public List<BooksDto> getPageOfPopularBook(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return getBooksDtoList(bi.getAllByAvg(nextPage).getContent());
    }
    public List<BooksDto> getPageOfRecentBook(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return getBooksDtoList(bi.findAllByOrderByPubDate(nextPage).getContent());
    }

    public List<BooksDto> getPageOfRecentBooksBetween(Date pubDate,
                                                      Date pubDate2,
                                                      Integer offset,
                                                      Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return getBooksDtoList(bi.findAllByPubDateBetweenOrderByPubDate(pubDate, pubDate2, nextPage).getContent());
    }

    public void exchangeImage(String savePath, String slug) {
        BooksEntity book = bi.findBySlug(slug);
        book.setImage(savePath);
        bi.save(book);
    }
}
