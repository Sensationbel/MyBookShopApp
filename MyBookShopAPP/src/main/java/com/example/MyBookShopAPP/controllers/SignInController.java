package com.example.MyBookShopAPP.controllers;

import com.example.MyBookShopAPP.dto.SearchWordDto;
import com.example.MyBookShopAPP.security.ContactConfirmationPayload;
import com.example.MyBookShopAPP.security.ContactConfirmationResponse;
import com.example.MyBookShopAPP.security.RegistrationForm;
import com.example.MyBookShopAPP.security.UserRegisterServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
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
        response.setResult(true);
        return response;
    }

    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload payload){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(true);
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
    public ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayload payload){
        return userRegister.login(payload);
    }

    @GetMapping("/my")
    public String handleMy(){
        return "my";
    }
}
