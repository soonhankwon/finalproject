package com.backendteam5.finalproject.controller;

import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import com.backendteam5.finalproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/search/courier")
    public List<Courier> searchCourier(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return adminService.searchCourier(userDetails);
    }

    @GetMapping("/search/courier/user")
    public List<Account> searchUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return adminService.searchUser(userDetails);
    }

    @GetMapping("/search/courier/route")
    public List<Courier> searchRoute(
            @RequestParam("subRoute") List<Long> subRoute,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adminService.searchRoute(subRoute, userDetails);
    }

    @GetMapping("/search/courier/sorted")
    public List<Courier> sortedCourier(
            @RequestParam("state")
    )
}