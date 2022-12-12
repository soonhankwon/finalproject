package com.backendteam5.finalproject.controller;

import com.backendteam5.finalproject.dto.*;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import com.backendteam5.finalproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {
    private final AdminService adminService;

    // 로그인후 페이지에서 자동
    @GetMapping("/main")
    public AdminMainDto getMainReport(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adminService.getMainReport(userDetails);
    }

    // route 눌럿을때 새창에서 자동
    @GetMapping("/show/{route}")
    public List<DeliveryAssignmentDto> selectRoute(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable String route) {
        return adminService.selectRoute(userDetails, route);
    }

    // 운송장에 대한 검색
    @GetMapping("/search/courier")
    public List<AdminCourierDto> searchByCouriers(@RequestParam(value = "courierId") List<Long> courierId){
        return adminService.searchByCouriers(courierId);
    }

    // 상세겁색에 확인 버튼 누를시
    @GetMapping("/search/details")
    public List<AdminCourierDto> searchByDetails(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 SearchReqDto reqDto){
        return adminService.searchByDetails(userDetails, reqDto);
    }

    // route 새창 페이지에서 완료 누를시
    @PostMapping("/update/delivery")
    public String updateDelivery(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @RequestBody UpdateDeliveryDto updateDeliveryDto){
        return adminService.updateDelivery(userDetails, updateDeliveryDto);
    }

    // courier의 state를 배송지연으로 바꿔야 될때
    @PatchMapping("/update/state")
    public String setStateDelay(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @RequestBody SetDeliveryPersonReqDto couriers){
        return adminService.setStateDelay(userDetails, couriers.getCouriers());
    }

    // courier에 직접할당을 해줄때
    @PatchMapping("/update/DeliverPerson")
    public String setDeliveryPerson(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @RequestBody SetDeliveryPersonReqDto reqDto){
        return adminService.setDeliveryPerson(userDetails, reqDto);
    }

    // 특정 courier를 상세하게 바꾸기 할때
    @PatchMapping("/update/courier/{courierId}")
    public CourierResUpdateDto updateCourier(@PathVariable Long courierId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @RequestBody @Validated CourierReqUpdateDto courierReqUpdateDto) {
        return adminService.updateCourier(courierId, userDetails, courierReqUpdateDto);
    }

    @Scheduled(cron = "0 1 0 * * ?")
    public void updateArrivalDateAndDeliveryPerson(){
        adminService.updateArrivalDateAndDeliveryPerson();
    }
}