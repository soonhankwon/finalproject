package com.backendteam5.finalproject.controller;

import com.backendteam5.finalproject.dto.AdminMainResDto;
import com.backendteam5.finalproject.dto.UpdateReqDto;
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

    @PatchMapping("/save/{courierId}")
    public CourierResUpdateDto updateCourier(@PathVariable Long courierId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @RequestBody CourierReqUpdateDto courierReqUpdateDto) {
        return adminService.updateCourier(courierId, userDetails, courierReqUpdateDto);
    }

    @PatchMapping("/save/courier")
    public CourierResUpdateDto updateCouriers(@RequestBody UpdateReqDto updateReqDto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adminService.updateCouriers(updateReqDto, userDetails);
    }
}