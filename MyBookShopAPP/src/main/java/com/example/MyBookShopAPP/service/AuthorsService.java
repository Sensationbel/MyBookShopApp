package com.example.MyBookShopAPP.service;

import com.example.MyBookShopAPP.dto.AuthorsDto;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.AuthorsInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
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
            authorsDto.setFirstName(author.getFirst_name());
            authorsDto.setLastName(author.getLast_name());

            authors.add(authorsDto);
        });
        return authors
                .stream()
                .collect(Collectors
                        .groupingBy((AuthorsDto a) -> {return a.getLastName().substring(0, 1);}));
    }
}
