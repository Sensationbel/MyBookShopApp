package org.example.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Getter
@Setter
public class BookIdToRemove {

    @NotNull(message = "Wrong id not found")
    private Integer id;
}
