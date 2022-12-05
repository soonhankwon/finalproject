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

    @PatchMapping("/api/save/check/{courierId}")
    public CourierResUpdateDto checkCourierState(@PathVariable Long courierId) {
        return courierService.checkCourierState(courierId);
    }

    @PatchMapping("/api/save/uncheck/{courierId}")
    public CourierResUpdateDto uncheckCourierState(@PathVariable Long courierId) {
        return courierService.uncheckCourierState(courierId);
    }

    @GetMapping("/api/search/user/courier")
    public SearchResponseDto searchFilter(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @RequestParam Long state) {
        return courierService.searchFilter(userDetails, state);
    }

    @GetMapping("/api/search/user/courier/customer")
    public SearchResponseDto searchCustomer(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @RequestParam String customer) {
        return courierService.searchCustomer(userDetails, customer);
    }
}
