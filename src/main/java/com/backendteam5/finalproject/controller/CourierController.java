package com.backendteam5.finalproject.controller;


import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.dto.SearchResponseDto;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import com.backendteam5.finalproject.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;


    @GetMapping("/test3")
    public String test3() {
        courierService.test3();
        return "성공";
    }
    @PatchMapping("/api/save/check/{courierId}")
    public CourierResUpdateDto checkCourierState(@PathVariable Long courierId) {
        System.out.println("courierId = " + courierId);
        return courierService.checkCourierState(courierId);
    }

    @PatchMapping("/api/save/uncheck/{courierId}")
    public CourierResUpdateDto uncheckCourierState(@PathVariable Long courierId) {
        return courierService.uncheckCourierState(courierId);
    }

    @GetMapping("/api/search/user/courier")
    public SearchResponseDto searchFilter(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @RequestParam Long state) {

        System.out.println(state);
        System.out.println(userDetails.getUsername());

        SearchResponseDto responseDto = courierService.searchFilter(userDetails, state);
        System.out.println("searchFilter = " + responseDto);

        return responseDto;
    }

    @GetMapping("/api/search/user/courier/customer")
    public SearchResponseDto searchCustomer(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestParam String customer) {

        return courierService.searchCustomer(userDetails, customer);
    }
}