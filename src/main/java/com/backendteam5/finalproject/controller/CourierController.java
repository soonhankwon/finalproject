package com.backendteam5.finalproject.controller;


import com.backendteam5.finalproject.dto.CourierReqUpdateDto;
import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import com.backendteam5.finalproject.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @PostMapping("/test")
    public void dummieTest() {
        courierService.createDommie();
    }
    @PatchMapping("/api/post/{username}")
    public CourierResUpdateDto updateCourier(@PathVariable String username,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                                             CourierReqUpdateDto courierReqUpdateDto) {
        return courierService.updateCourier(username, userDetails, courierReqUpdateDto);
    }
    @PatchMapping("/api/post/{courierId}/check")
    public CourierResUpdateDto checkCourierState(@PathVariable Long courierId,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 CourierReqUpdateDto courierReqUpdateDto) {
        return courierService.checkCourierState(courierId, userDetails, courierReqUpdateDto);
    }
    @PatchMapping("/api/post/{courierId}/uncheck")
    public CourierResUpdateDto uncheckCourierState(@PathVariable Long courierId,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   CourierReqUpdateDto courierReqUpdateDto) {
        return courierService.uncheckCourierState(courierId, userDetails, courierReqUpdateDto);
    }

}
