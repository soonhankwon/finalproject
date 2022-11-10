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
        System.out.println("받은 Path: " + courierId);
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

    //운송장 번호 기준 조회하여 택배기사에게 할당 => 상세 바꾸는 기능
    @PatchMapping("/save/{courierId}")
    public CourierResUpdateDto updateCourier(@PathVariable Long courierId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @RequestBody CourierReqUpdateDto courierReqUpdateDto) {
        return adminService.updateCourier(courierId, userDetails, courierReqUpdateDto);
    }

    //서브라우트 번호로 배송 담당자에게 일괄 할당
    @PatchMapping("/save/subroute/{usernameId}/courier")
    public CourierResUpdateDto updateCourierBySubRoute(@PathVariable Long usernameId,
                                                       @RequestParam(value = "subRoutes", required = false) List<Integer> subRoutes,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adminService.updateCourierBySubRoute(usernameId, subRoutes, userDetails);
    }
}