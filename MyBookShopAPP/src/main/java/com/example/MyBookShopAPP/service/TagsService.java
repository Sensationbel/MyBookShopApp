package com.example.MyBookShopAPP.service;

import com.example.MyBookShopAPP.dto.TagsDto;
import com.example.MyBookShopAPP.model.genre.GenreEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.GenresInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TagsService {

    private final GenresInterface gi;

    public List<TagsDto> getPageOfTags() {

        List<GenreEntity> genreEntityList = gi.findAll();
        Map<GenreEntity, List<GenreEntity>> genreEntityMap = new HashMap<>();
        genreEntityList. forEach(genre -> {
            List<GenreEntity> entityListByGenre = new ArrayList<>();
            getGenreEntityListByGenre(genre, genreEntityList, entityListByGenre);
            genreEntityMap.put(genre, entityListByGenre);
        });

        return createTagsDto(genreEntityMap);
    }

    private void getGenreEntityListByGenre(GenreEntity genre,
                                           List<GenreEntity> genreEntityList,
                                           List<GenreEntity> entityListByGenre) {

        List<GenreEntity> list = genreEntityList.stream().filter(g ->
            genre.getId() != g.getId() && genre.getId() == g.getParentId())
        .toList();
        entityListByGenre.addAll(list);

        if(list.size() > 0){
            list.forEach(g -> getGenreEntityListByGenre(g, genreEntityList, entityListByGenre));
        }
    }

    private List<TagsDto> createTagsDto(Map<GenreEntity, List<GenreEntity>> genreEntityMap){

        List<TagsDto> tagsDtoList = new ArrayList<>();

        double maxCount = getMaxCount(genreEntityMap);
        for (Map.Entry<GenreEntity, List<GenreEntity>> map : genreEntityMap.entrySet()) {
            GenreEntity genre = map.getKey();
            TagsDto tagsDto = getTagsDto(genre);
            int count = (int) map.getValue().stream().map(g -> g.getBooks().size()).count() + genre.getBooks().size();
            getTagsClass(tagsDto, maxCount, count);
            tagsDtoList.add(tagsDto);
        }
        return tagsDtoList;
    }

    private TagsDto getTagsDto(GenreEntity genre) {
        TagsDto tagsDto = new TagsDto();
        tagsDto.setGenresName(genre.getName());
        tagsDto.setSlug(genre.getSlug());
        return tagsDto;
    }

    public TagsDto getTagsDtoBySlug(String slug) {
        return getTagsDto(gi.findBySlug(slug));
    }

    private void getTagsClass(TagsDto tagsDto, double maxCount, int count) {
        maxCount = maxCount/100;
        if(count >= (maxCount*80)){
        tagsDto.setTagsClass(TagsDto.TagsClass.TAGLG);
        } else if(count < maxCount*80 && count >= maxCount*60){
            tagsDto.setTagsClass(TagsDto.TagsClass.TAGMD);
        } else if(count < maxCount*60 && count >= maxCount*40){
            tagsDto.setTagsClass(TagsDto.TagsClass.TAG);
        }else if(count < maxCount*40 && count >= maxCount*20){
            tagsDto.setTagsClass(TagsDto.TagsClass.TAGSM);
        }else if(count < maxCount*20){
            tagsDto.setTagsClass(TagsDto.TagsClass.TAGXS);

        }
    }

    private  Integer getMaxCount(Map<GenreEntity, List<GenreEntity>> genreEntityMap) {

        int maxCount = 0;
        for (Map.Entry<GenreEntity, List<GenreEntity>> map : genreEntityMap.entrySet()) {
            int count =  map.getValue().
                    stream().
                    map(genre -> genre.getBooks().size()).
                    reduce(Integer::sum).
                    orElse(0);
            count += map.getKey().getBooks().size();
            if(count > maxCount){
                maxCount = count;
            }
        }
        return maxCount;
    }

}
