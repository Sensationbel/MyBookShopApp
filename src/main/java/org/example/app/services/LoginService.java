package org.example.app.services;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Service;

@Log4j
@Service
public class LoginService {

    public boolean authenticate(LoginForm loginForm){
        log.info("try auth with user_form :" + loginForm);
        return loginForm.getUsername().equals("root") && loginForm.getPassword().equals("123");
    }
}
