package com.example.MyBookShopAPP.service;

import com.example.MyBookShopAPP.dto.AuthorsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorsService {

    private final JdbcTemplate jdbcTemplate;

    public List<AuthorsDto> getAuthorsData(){
        List<AuthorsDto> authors= jdbcTemplate.query("SELECT first_name f, last_name l FROM AUTHORS", (ResultSet rs, int rowNum) -> {
            AuthorsDto authorsDto = new AuthorsDto();
            authorsDto.setFirstName(rs.getString("first_name"));
            authorsDto.setLastName(rs.getString("last_name"));
            return authorsDto;
        });
        return new ArrayList<>(authors);
    }
}
