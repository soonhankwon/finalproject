package com.backendteam5.finalproject.controller;

import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            model.addAttribute("username", userDetails.getUsername());
        } catch (NullPointerException e) {
            return "redirect:/account/login";
        }

        if (userDetails.getUser().getRole() == UserRoleEnum.ADMIN) {
            return "index1";
        } else{
            model.addAttribute("username", userDetails.getUsername());
//            return "redirect:/api/search/user/courier?state=0";
            return "index2";
        }
    }

    // admin detail 새창 띄울때 필요함
    @GetMapping("/detailSearch")
    public String detail(){
        return "detailSearch";
    }
}
