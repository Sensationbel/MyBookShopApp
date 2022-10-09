package com.example.MyBookShopAPP.service;

import com.example.MyBookShopAPP.dto.AuthorsDto;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.AuthorsInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorsService {

    private final AuthorsInterfaces ai;

//    public List<AuthorsDto> getAuthorsData(){
//
//        return authors ;
//    }

    public Map<String, List<AuthorsDto>> getAuthorsMap() {
        List<AuthorsDto> authors = new ArrayList<>();
        ai.findALL().forEach(author ->{
            AuthorsDto authorsDto = new AuthorsDto();
            String[] name = author.getName().split(" ");
            authorsDto.setFirstName(name[0]);
            authorsDto.setLastName(name[1]);

            authors.add(authorsDto);
        });
        return authors
                .stream()
                .collect(Collectors
                        .groupingBy((AuthorsDto a) -> {return a.getLastName().substring(0, 1);}));
    }
}
