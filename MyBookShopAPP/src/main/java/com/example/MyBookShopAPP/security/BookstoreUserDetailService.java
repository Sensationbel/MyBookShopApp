package com.example.MyBookShopAPP.security;

import com.example.MyBookShopAPP.model.user.UserContactEntity;
import com.example.MyBookShopAPP.repositories.jpa_interfaces.UserContactInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookstoreUserDetailService implements UserDetailsService {

    private final UserContactInterfaces userContactInterfaces;

    @Override
    public UserDetails loadUserByUsername(String contact) throws UsernameNotFoundException {
        UserContactEntity user = userContactInterfaces.findByContact(contact);
        if (user == null) {
            return new BookstoreUserDetails(user);
        } else {
            throw new UsernameNotFoundException("User not found doh!");
        }
    }
}
