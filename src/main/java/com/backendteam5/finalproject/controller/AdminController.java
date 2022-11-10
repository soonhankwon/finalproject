package com.backendteam5.finalproject.controller;

import com.backendteam5.finalproject.dto.AdminMainResDto;
import com.backendteam5.finalproject.dto.CourierReqUpdateDto;
import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import com.backendteam5.finalproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/search/courier")
    public AdminMainResDto findAll(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return adminService.findAll(userDetails);
    }

    @GetMapping("/search/courier/{courierId}")
    public AdminMainResDto searchCourier(@PathVariable Long courierId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return adminService.searchCourier(courierId, userDetails);
    }

    @GetMapping("/search/courier/sorted")
    public AdminMainResDto sortedCourier(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "subRoute", required = false) List<Integer> subRoute,
            @RequestParam("state") int state,
            @RequestParam("arri") Boolean arri,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return adminService.sortedCourier(username, subRoute, state, arri, userDetails);
    }
    @PatchMapping("/post/{courierId}")
    public CourierResUpdateDto updateCourier(@PathVariable Long courierId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @RequestBody CourierReqUpdateDto courierReqUpdateDto) {
        return adminService.updateCourier(courierId, userDetails, courierReqUpdateDto);
    }
    @PatchMapping("/post/{subRouteId}/updateBySubRoute")
    public CourierResUpdateDto updateCourierBySubRoute(@PathVariable int subRouteId,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @RequestBody CourierReqUpdateDto courierReqUpdateDto) {
        return adminService.updateCourierBySubRoute(subRouteId, userDetails, courierReqUpdateDto);
    }
}