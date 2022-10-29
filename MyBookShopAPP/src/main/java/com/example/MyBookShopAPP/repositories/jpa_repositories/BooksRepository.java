package com.example.MyBookShopAPP.repositories.jpa_repositories;

import com.example.MyBookShopAPP.model.BooksEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BooksRepository extends JpaRepository<BooksEntity, Integer> {

    Page<BooksEntity> findBooksEntitiesByTitleContaining(String title,
                                                         Pageable pageable);

    Page<BooksEntity> findByOrderByPubDate(Pageable pageable);

    Page<BooksEntity> findAllByPubDateBetweenOrderByPubDate(Date pubDate,
                                                            Date pubDate2,
                                                            Pageable pageable);
    @Query(value = "SELECT *, (b.count_bought + 0.7*b.count_put_basket + 0.3*b.count_postponed)" +
            " AS summa FROM Books b ORDER by summa  DESC", nativeQuery = true)
    Page<BooksEntity> getAllByAvg(Pageable pageable);

    @Query(value = "WITH RECURSIVE generation AS (" +
            "SELECT gs.id, gs.name, gs.parent_id, gs.slug, 0 AS generation_number " +
            "FROM Genre gs " +
            "WHERE gs.slug=?1 " +
            "UNION ALL " +
            "SELECT child.id, child.name, child.parent_id, child.slug, generation_number+1 AS generation_number " +
            "FROM Genre child JOIN generation g ON g.id = child.parent_id) " +
            " SELECT DISTINCT ON (BooksSort.title) * FROM (" +
            "SELECT * FROM Books b " +
            "INNER JOIN Book2genre bg ON bg.book_id = b.id " +
            "INNER JOIN Genre gr ON gr.id = bg.genre_id " +
            "INNER JOIN generation g ON g.id = gr.id " +
            "LEFT JOIN Genre parent ON parent.id=g.parent_id) AS BooksSort ORDER BY BooksSort.title", nativeQuery = true)
    List<BooksEntity> getBooksEntitiesByGenres(Pageable pageable, String slug);

    @Query(value = "SELECT * FROM Books b " +
            "JOIN Book2author ba ON ba.book_id = b.id " +
            "JOIN Authors a ON a.id = ba.author_id " +
            "WHERE a.id=?1", nativeQuery = true)
    List<BooksEntity> findAllByAuthors(Pageable pageable, int id);

    BooksEntity findBySlug(String slug);
    BooksEntity save(BooksEntity book);
}
