package com.example.MyBookShopAPP.service;

import com.example.MyBookShopAPP.dto.GenresDto;
import com.example.MyBookShopAPP.model.genre.GenreEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.GenresInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenresService {

    private final GenresInterfaces gi;

    public List<GenresDto> getParentsListGenresEntity(){

        List<GenresDto> genresDtoList = new ArrayList<>();
        List<GenreEntity> parentsList = gi.findAllByParentIdIsNull();

        parentsList.forEach(g -> {
            genresDtoList.add(getGenresDto(g));
        });
        getPrint(genresDtoList);
        return genresDtoList;
    }

    private void getPrint(List<GenresDto> genresDtoList) {
        genresDtoList.forEach(g -> {
            if(g.getChildList().size()!=0){
                System.out.println("Parent -> "
                        + g.getParentId()
                        + "  " + g.getGenresName() + " (" + g.getChildList().size() + ")");
               getPrint(g.getChildList());
            } else System.out.println("Child ->  "
                    + g.getParentId()
                    + "  " + g.getGenresName() + " (" + g.getChildList().size() + ")");
        });
    }

    private GenresDto getGenresDto(GenreEntity g) {
        List<GenreEntity> genreEntityChildList = new ArrayList<>(gi.findAllByParentId(g.getId()));
        GenresDto genresDto;

        if(genreEntityChildList.size() == 0){
            genresDto = createGenresDto(g);
        } else {
            genresDto = createGenresDto(g);
            List<GenresDto> list = genresDto.getChildList();
            genreEntityChildList.forEach(genres -> {
                GenresDto genre = getGenresDto(genres);
                genresDto.setBooksCount(genresDto.getBooksCount() + genre.getBooksCount());
                list.add(genre);
            });
        }
        return genresDto;
    }

    private GenresDto createGenresDto(GenreEntity g){
        GenresDto genresDto = new GenresDto();
        genresDto.setId(g.getId());
        genresDto.setParentId(g.getParentId());
        genresDto.setGenresName(g.getName());
        genresDto.setBooksCount(g.getBooks().size());
        return genresDto;
    }


}
