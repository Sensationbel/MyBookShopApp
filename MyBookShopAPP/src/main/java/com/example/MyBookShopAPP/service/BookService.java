package com.example.MyBookShopAPP.service;

import com.example.MyBookShopAPP.dto.BooksDto;
import com.example.MyBookShopAPP.dto.CartDto;
import com.example.MyBookShopAPP.dto.SlugBookDto;
import com.example.MyBookShopAPP.model.BooksEntity;
import com.example.MyBookShopAPP.model.book.links.Book2AuthorEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.AuthorsInterface;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.Book2AuthorInterface;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.BooksInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookService {

    private final BooksInterface bi;
    private final Book2AuthorInterface b2AuthInt;
    private final AuthorsInterface aServ;

    public List<BooksDto> getBooksDtoList(List<BooksEntity> list) {
        List<BooksDto> books = new ArrayList<>();
        list.forEach(book -> {
            BooksDto booksDto = createBooksDto(book);
            books.add(booksDto);
        });
        return books;
    }

    public SlugBookDto getSlugBookDtoBySlug(String slug) {
        BooksEntity book = bi.findBySlug(slug);
        SlugBookDto slugBook = new SlugBookDto();
        slugBook.setBookId(book.getId());
        slugBook.setTitle(book.getTitle());
        slugBook.setAuthor(getAuthors(book));
        slugBook.setDescription(book.getDescription());
        slugBook.setSlug(book.getSlug());
        slugBook.setImage(book.getImage());
        slugBook.setPrice(book.getPrice());
        slugBook.setDiscountPrice((int)getPriceOld(book));
        slugBook.addTags(book.getGenres());
        slugBook.addBookFileList(book);
        slugBook.isBestseller((book.getIsBestseller()));
        return slugBook;
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

    public CartDto getBookCart(String cartContents) {
        cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
        cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
        String[] cookieSlugs = cartContents.split("/");
        List<BooksDto> books = getBooksDtoList(bi.findAllBySlug(cookieSlugs));
        return new CartDto(books);
    }
}
