package com.backendteam5.finalproject.controller;


import com.backendteam5.finalproject.dto.CourierReqUpdateDto;
import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.dto.SearchResponseDto;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import com.backendteam5.finalproject.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @PostMapping("/test")
    public String dummieTest() {
        courierService.createDommie();
        return "redirect:/";
    }
    @PatchMapping("/api/save/check")
    public CourierResUpdateDto checkCourierState(@RequestParam(value = "courierId") Long courierId) {
        return courierService.checkCourierState(courierId);
    }
    @PatchMapping("/api/save/uncheck")
    public CourierResUpdateDto uncheckCourierState(@RequestParam(value = "courierId") Long courierId) {
        return courierService.uncheckCourierState(courierId);
    }

    @GetMapping("/api/search/user/courier")
    public SearchResponseDto searchFilter(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @RequestParam Long state,
                               Model model ) {

        System.out.println(state);
        System.out.println(userDetails.getUsername());

        SearchResponseDto responseDto = courierService.searchFilter(userDetails, state);
        System.out.println("searchFilter = " + responseDto);

        model.addAttribute("searchFilter", responseDto);
        model.addAttribute("username", userDetails.getUsername());
//        return "index2";
        return responseDto;
    }

//    @GetMapping("/api/search/user/courier/customer")
//    public SearchResponseDto searchCustomer(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                 @RequestParam String customer) {
//
//        return courierService.searchCustomer(userDetails, customer);
//    }
}
