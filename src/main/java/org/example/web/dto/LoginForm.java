package org.example.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginForm {

    private String username;
    private String password;
}
