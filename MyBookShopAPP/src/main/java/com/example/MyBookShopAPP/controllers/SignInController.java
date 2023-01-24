package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.SearchWordDto;
import com.example.MyBookShopAPP.security.ContactConfirmationPayload;
import com.example.MyBookShopAPP.security.ContactConfirmationResponse;
import com.example.MyBookShopAPP.security.RegistrationForm;
import com.example.MyBookShopAPP.security.UserRegisterServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SignInController {

    private final UserRegisterServices userRegister;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @GetMapping("/signin")
    public String handelSignIn(){
        return "signin";
    }

    @GetMapping("/signup")
    public String handelSignUp(Model model){
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestContactConfirmation(@RequestBody ContactConfirmationPayload payload){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload payload){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @PostMapping("/reg")
    public String handleUserRegistration(RegistrationForm registrationForm, Model model){
        userRegister.registerNewUser(registrationForm);
        model.addAttribute("regOk", true);
        return "signin";
    }

    @PostMapping("/login")
    @ResponseBody
    public ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayload payload,
                                                   HttpServletResponse response){
        ContactConfirmationResponse loginResponse = userRegister.jwtLogin(payload);
        Cookie cookie = new Cookie("token", loginResponse.getResult());
        response.addCookie(cookie);
        log.info("JWT token {}", loginResponse);
        return loginResponse;
    }

    @GetMapping("/my")
    public String handleMy(){
        return "my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model){
        model.addAttribute("currentUser", userRegister.getCurrentUser());
        return "profile";
    }

    /*
    При добавлении JWT token, logout перенесли в SecurityConfig configure
     */

//    @GetMapping("/logout")
//    public String handleLogout(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        SecurityContextHolder.clearContext();
//
//        if(session != null){
//            session.invalidate();
//        }
//
//        for(Cookie cookie : request.getCookies()){
//            cookie.setMaxAge(0);
//        }
//
//        return "redirect:/";
//    }
}
