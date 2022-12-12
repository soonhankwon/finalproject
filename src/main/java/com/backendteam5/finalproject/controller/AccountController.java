package com.backendteam5.finalproject.controller;

import com.backendteam5.finalproject.dto.SignupRequestDto;
import com.backendteam5.finalproject.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/account/login")
    public String login() {
        return "login";
    }

    @GetMapping("/account/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/account/signup")
    public String registerAccount(@Validated SignupRequestDto requestDto){
        accountService.registerAccount(requestDto);
        return "login";
    }
}
