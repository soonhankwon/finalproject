package com.backendteam5.finalproject.controller;

import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.AccountRepository;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final AccountRepository accountRepository;
    private final CourierRepository courierRepository;
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            model.addAttribute("username", userDetails.getUsername());
        } catch (NullPointerException e) {
            return "redirect:/user/login";
        }

        if (userDetails.getUser().getRole() == UserRoleEnum.ADMIN) {
            String route = userDetails.getUser().getRoute();
            model.addAttribute("admin_role", true);
            model.addAttribute("userList", accountRepository.findByRouteAndRole(route, UserRoleEnum.USER));
            model.addAttribute("courierList", courierRepository.findByRouteOrderByArrivalDateAsc(route));
            model.addAttribute("username", userDetails.getUsername());
            return "index1";
        } else{
            model.addAttribute("username", userDetails.getUsername());
            return "index2";
        }
    }
}
