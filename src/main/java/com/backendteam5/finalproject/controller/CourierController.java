package com.backendteam5.finalproject.controller;


import com.backendteam5.finalproject.dto.SearchResponseDto;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import com.backendteam5.finalproject.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @PostMapping("/test")
    public String dummieTest() {
        courierService.createDommie();
        return "index2";
    }

    @GetMapping("/api/search/user/courier")
    public String searchFilter(@AuthenticationPrincipal UserDetailsImpl userDetails,
                             @RequestParam Long state,
                              Model model ) {

        System.out.println(state);
        System.out.println(userDetails.getUsername());

        SearchResponseDto responseDto = courierService.searchFilter(userDetails, state);
        System.out.println("searchFilter = " + responseDto);

        model.addAttribute("searchFilter", responseDto);
        model.addAttribute("username", userDetails.getUsername());
        return "index2";
    }

    @GetMapping("/api/search/user/courier/customer")
    public String searchCustomer(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @RequestParam String customer,
                                 Model model) {

        courierService.searchCustomer(userDetails, customer);
        return "test";
    }


}
