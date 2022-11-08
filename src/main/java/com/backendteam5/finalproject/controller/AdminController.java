package com.backendteam5.finalproject.controller;

import com.backendteam5.finalproject.dto.MainResponseDto;
import com.backendteam5.finalproject.dto.MainlResponseDto;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import com.backendteam5.finalproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/search/courier")
    public MainResponseDto searchall(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return adminService.searchAll(userDetails);
    }

    @GetMapping("/search/courier")
    public MainResponseDto searchroute(
            @RequestParam("route") String route,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adminService.searchRoute(route, userDetails);
    }
}
