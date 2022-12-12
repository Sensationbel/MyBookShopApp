package com.example.MyBookShopAPP.security;

import com.example.MyBookShopAPP.model.enums.ContactType;
import com.example.MyBookShopAPP.model.user.UserContactEntity;
import com.example.MyBookShopAPP.model.user.UserEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.UserContactInterfaces;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.UsersInterfaces;
import com.example.MyBookShopAPP.repositories.jpa_services.UsersJpaServices;
import com.example.MyBookShopAPP.security.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserRegisterServices {

    private final UserContactInterfaces userContactInterfaces;
    private final JWTUtil jwtUtil;
    private final BookstoreUserDetailService bookstoreUserDetailService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public void registerNewUser(RegistrationForm registrationForm) {

        if(userContactInterfaces.findByContact(registrationForm.getEmail())==null) {
            UserContactEntity userContact = new UserContactEntity();
            UserEntity userEntity = new UserEntity();
            userEntity.setName(registrationForm.getName());
            userEntity.setHash(passwordEncoder.encode(registrationForm.getPass()));
            userEntity.setRegTime(LocalDateTime.now());
            userContact.setContact(registrationForm.getEmail());
            userContact.setType(ContactType.EMAIL);
            userContact.setApproved((short) 0);
            userContact.setCodeTime(LocalDateTime.now());
            userContact.setUsers(userEntity);
            userContactInterfaces.save(userContact);
        }

    }

    public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact()
                , payload.getCode()));
        BookstoreUserDetails userDetails = (BookstoreUserDetails) bookstoreUserDetailService
                .loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    public Object getCurrentUser() {
        BookstoreUserDetails userDetails = (BookstoreUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return userDetails.getUser();
    }
}
