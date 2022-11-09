package com.backendteam5.finalproject.controller;

import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            model.addAttribute("username", userDetails.getUsername());
        } catch (NullPointerException e) {
            return "redirect:/user/login";
        }

        if (userDetails.getUser().getRole() == UserRoleEnum.ADMIN) {
            model.addAttribute("admin_role", true);
            model.addAttribute("username", userDetails.getUsername());
            return "index1";
        } else{
            model.addAttribute("username", userDetails.getUsername());
            return "redirect:/api/search/user/courier?state=0";
        }
    }
}
