package com.example.MyBookShopAPP.errors;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;

@Getter
@Setter
public class JWTTokenInvalidException extends Exception{

    private final String message;
    public JWTTokenInvalidException(String message) {
        this.message = message;
    }

//    private String getMessage(String name) {
//
//        String message = null;
//        switch (name){
//            case ("SignatureException") -> {
//                return "Calculating a signature or verifying an existing signature of a JWT failed";}
//            case ("io.jsonwebtoken.MalformedJwtException") -> {
//                return "JWT was not correctly constructed and should be rejected";
//            }
//            case ("UnsupportedJwtException") -> {return "JWT does not match the format expected by the application";}
////            case ()
//            default -> {
//                return "";
//            }
//        }
//
//    }
}
