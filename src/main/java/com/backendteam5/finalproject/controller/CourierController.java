package com.backendteam5.finalproject.controller;


import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import com.backendteam5.finalproject.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @PostMapping("/test")
    public void dummieTest() {
        courierService.createDommie();
    }

    @GetMapping("/api/search/user")
    public List<Courier> searchAll(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return courierService.searchAll(userDetails);
    }

    @GetMapping("/api/search/user/courier")
    public List<Courier> searchFilter(@AuthenticationPrincipal UserDetailsImpl userDetails,
                             @RequestParam Long state,
                             @RequestParam Long arri) {

        System.out.println(state);
        System.out.println(arri);
        System.out.println(userDetails.getUsername());

        return courierService.searchFilter(userDetails, state, arri);
    }
}
