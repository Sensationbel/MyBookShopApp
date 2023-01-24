package com.example.MyBookShopAPP.security;

import com.example.MyBookShopAPP.security.blackList.BlackListingService;
import com.example.MyBookShopAPP.security.jwt.JWTRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BookstoreUserDetailService bookstoreUserDetailService;
    private final JWTRequestFilter filter;
    private final BlackListingService blackListingService;

    @Autowired
    public SecurityConfig(BookstoreUserDetailService bookstoreUserDetailService
            , JWTRequestFilter filter
            , BlackListingService blackListingService) {
        this.bookstoreUserDetailService = bookstoreUserDetailService;
        this.filter = filter;
        this.blackListingService = blackListingService;
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(bookstoreUserDetailService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/my", "/profile").authenticated()//.hasRole("USER")
                .antMatchers("/**").permitAll()
                .and().formLogin()
                .loginPage("/signin").failureUrl("/signin")
                .and().logout().logoutUrl("/logout").addLogoutHandler((request, response, authentication) ->
                        blackListingService.blackListJwt(getJwtTokenFromRequest(request)))
                .logoutSuccessUrl("/signin").deleteCookies("token")
                .and().oauth2Login()
                .and().oauth2Client();

//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    private String getJwtTokenFromRequest(HttpServletRequest request) {
        String tokens = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("token"))
                .map(cookie -> cookie.getValue())
                .findFirst()
                .orElse("");
        return tokens;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
