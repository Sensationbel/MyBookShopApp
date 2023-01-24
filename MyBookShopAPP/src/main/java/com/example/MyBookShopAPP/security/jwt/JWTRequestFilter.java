package com.example.MyBookShopAPP.security.jwt;

import com.example.MyBookShopAPP.errors.JWTTokenInvalidException;
import com.example.MyBookShopAPP.security.BookstoreUserDetailService;
import com.example.MyBookShopAPP.security.BookstoreUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2
@RequiredArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final BookstoreUserDetailService bookstoreUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        Cookie[] cookies = request.getCookies();
        try {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        token = cookie.getValue();
                        if (jwtUtil.isBlackList(token)) {
                            username = jwtUtil.extractUsername(token);
                        }
                    }

                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        BookstoreUserDetails userDetails =
                                (BookstoreUserDetails) bookstoreUserDetailService.loadUserByUsername(username);
                        if (jwtUtil.validateToken(token, userDetails)) {
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(
                                            userDetails, null, userDetails.getAuthorities());

                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                }
            }
        } catch (SignatureException
                 | MalformedJwtException
                 | UnsupportedJwtException
                 | IllegalArgumentException
                 | ExpiredJwtException
                 | JWTTokenInvalidException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error(ex);
//            SecurityContextHolder.getContext().setAuthentication(null);
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
