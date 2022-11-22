package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.*;
import com.example.MyBookShopAPP.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.util.StringJoiner;


@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
@Log4j2
public class BooksController {

    private final BookService bookService;
    private final ResourceStorage storage;
    private final RateBooksService rateBooksService;
    private final AuthorsService authorsService;
    private final ReviewService reviewService;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @GetMapping("/recommended")
    @ResponseBody
    public BooksPageDto getRecommendedBooks(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfRecommendedBook(offset, limit));
    }

    @GetMapping("/popular/main")
    @ResponseBody
    public BooksPageDto getPopularBooks(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfPopularBook(offset, limit));
    }

    @GetMapping("/recent/main")
    @ResponseBody
    public BooksPageDto getRecentBooks(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfRecentBook(offset, limit));
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable("slug") String slug,
                           @CookieValue(value = "booksWithRate", required = false) String booksWithRate,
                           HttpSession session,
                           Model model){
        if(booksWithRate == null || !booksWithRate.contains(slug)){
            session.setAttribute("isRated", false);
        } else session.setAttribute("isRated", true);
        model.addAttribute("slugBook", bookService.getSlugBookDtoBySlug(slug));
        model.addAttribute("rateBooks", rateBooksService.getStatisticRate(slug));
        model.addAttribute("usersReview", reviewService.getUsersReviewByBooksSlug(slug));
        return "/books/slug";
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBookImage(@PathVariable("slug") String slug,
                                   @RequestParam("file") MultipartFile file) throws IOException {

        String folder = "/image/";
        String savePath = storage.saveNewBookImage(file, slug, folder);
        bookService.exchangeImage(savePath, slug);
        return ("redirect:/books/" + slug);
    }

    @GetMapping("/author/{id}")
    public String getBooksByAuthorId(@PathVariable("id") int id,
                                     Model model) {
        model.addAttribute("authorDto", authorsService.getAuthorsDtoById(id));
        model.addAttribute("booksAuthor", authorsService.getBooksByAuthorId(0, 5, id));
        return "/books/author";
    }

    @GetMapping("/author/page/{id}")
    @ResponseBody
    public BooksPageDto getPageBooksByAuthorId(@PathVariable("id") int id,
                                               @RequestParam("offset") int offset,
                                               @RequestParam("limit") int limit) {
        return new BooksPageDto(authorsService.getBooksByAuthorId(offset, limit, id));
    }

    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash)
            throws IOException {
        Path path = storage.getBookFilePath(hash);
        log.info("book file path: " + path);

        MediaType mediaType = storage.getBookFileMime(hash);
        log.info("book file media type: " + mediaType);

        byte[] data = storage.getBookFileByteArray(hash);
        log.info("book file data len: " + data.length);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }

    @PostMapping("/rateBook")
    @ResponseBody
    public ResponseEntity<?> addRateBook(@RequestParam("bookId") String bookId,
                                         @RequestParam("value") Integer value,
                                         HttpServletResponse response,
                                         @CookieValue(value = "booksWithRate", required = false) String booksWithRate){

        if(booksWithRate == null || booksWithRate.equals("")){
            Cookie cookie = new Cookie("booksWithRate", bookId);
            cookie.setPath("/books");
            response.addCookie(cookie);
        } else if(!booksWithRate.contains(bookId)){
            StringJoiner cookieRate = new StringJoiner("/");
            cookieRate.add(booksWithRate).add(bookId);
            Cookie cookie = new Cookie("booksWithRate", cookieRate.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
        }
        log.info("book with slug " + bookId + " was rated") ;
        return ResponseEntity.ok().
                body(rateBooksService.getResultChangedRateBook(bookId, value));
    }

}
