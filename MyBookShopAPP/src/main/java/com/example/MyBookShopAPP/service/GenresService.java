package com.example.MyBookShopAPP.service;

import com.example.MyBookShopAPP.dto.BooksDto;
import com.example.MyBookShopAPP.dto.GenresDto;
import com.example.MyBookShopAPP.model.BooksEntity;
import com.example.MyBookShopAPP.model.genre.GenreEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.BooksInterface;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.GenresInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GenresService {

    private final GenresInterface gi;
    private final BooksInterface bi;
    private final BookService bookService;

    public List<GenresDto> getParentsListGenresEntity() {

        List<GenresDto> genresDtoList = new ArrayList<>();
        List<GenreEntity> parentsList = gi.findAllByParentIdIsNull();

        parentsList.forEach(g -> genresDtoList.add(getGenresDto(g)));
        return genresDtoList.stream().sorted(Comparator.comparingInt(GenresDto::getBooksCount).reversed()).toList();
    }

    private GenresDto getGenresDto(GenreEntity genreEntity) {
        List<GenreEntity> genreEntityChildList = new ArrayList<>(gi.findAllByParentId(genreEntity.getId()));
        GenresDto genresDto;

        if (genreEntityChildList.size() == 0) {
            genresDto = createGenresDto(genreEntity);
        } else {
            genresDto = createGenresDto(genreEntity);
            List<GenresDto> list = genresDto.getChildList();
            genreEntityChildList.forEach(genres -> {
                GenresDto genre = getGenresDto(genres);
                genresDto.setBooksCount(genresDto.getBooksCount() + genre.getBooksCount());
                list.add(genre);
            });
        }
        genresDto.getChildList().sort(Comparator.comparingInt(GenresDto::getBooksCount));
        return genresDto;
    }

    private GenresDto createGenresDto(GenreEntity genreEntity) {
        GenresDto genresDto = new GenresDto();
        genresDto.setId(genreEntity.getId());
        genresDto.setSlug(genreEntity.getSlug());
        genresDto.setParentId(genreEntity.getParentId());
        genresDto.setGenresName(genreEntity.getName());
        genresDto.setBooksCount(genreEntity.getBooks().size());
        return genresDto;
    }

    public List<BooksDto>  getBooksDtoBySlug(int offset, int limit, String slug) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.by("pub_Date").descending());
        List<BooksEntity> list= bi.getBooksEntitiesByGenres(nextPage, slug);
        return bookService.getBooksDtoList(list);
    }

    public List<GenreEntity> getGenresSlugAndName(String slug) {
        List<GenreEntity>  slugAndName = new ArrayList<>();
        GenreEntity genre = gi.findBySlug(slug);
        slugAndName.add(genre);
        Integer parentId = genre.getParentId();
        while (parentId != null) {
            genre = gi.findById(parentId);
            slugAndName.add(0, genre);
            parentId = genre.getParentId();
        }
        return slugAndName;
    }
}
