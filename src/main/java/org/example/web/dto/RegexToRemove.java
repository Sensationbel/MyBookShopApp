package org.example.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class RegexToRemove {

    @Pattern(regexp = "^(author|title|size)\\s+[a-zA-Z\\d]+", message = "regex is not corrected")
    private String regex;
}
