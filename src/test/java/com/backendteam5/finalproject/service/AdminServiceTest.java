//package com.backendteam5.finalproject.service;
//
//import com.backendteam5.finalproject.dto.*;
//import com.backendteam5.finalproject.entity.Account;
//import com.backendteam5.finalproject.repository.AccountRepository;
//import com.backendteam5.finalproject.security.UserDetailsImpl;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.*;
//
//@SpringBootTest
//@Transactional
//class AdminServiceTest {
//    @Autowired
//    private AdminService adminService;
//    @Autowired
//    private AccountRepository accountRepository;
//
////    @DisplayName("get Main info")
////    @Test
////    void getMainReport() {
////        UserDetailsImpl userDetails = getUserDetail();
////
////        AdminMainDto mainReport = adminService.getMainReport(userDetails);
////
////        System.out.println("조회된 user이름 들 : "+mainReport.getUserlist().get(0).getUsername());
////        System.out.println("조회된 user이름 들 : "+mainReport.getUserlist().get(1).getUsername());
////        System.out.println("조회된 Route별 count : "+mainReport.getRouteCount());
////        System.out.println("조회된 User 직접할당 Count : "+mainReport.getDirectAssignment());
////        System.out.println("조회된 User 임시할당 Count : "+mainReport.getTempAssignment());
////    }
////
////    @DisplayName("get Route info")
////    @Test
////    void selectRoute() {
////        UserDetailsImpl userDetails = getUserDetail();
////
////        System.out.println(adminService.selectRoute(userDetails, "A"));
////    }
////
////    @DisplayName("update Delivery")
////    @Test
////    void updateDelivery() {
////        UserDetailsImpl userDetails = getUserDetail();
////
////        UpdateDeliveryDto updateDeliveryDto = new UpdateDeliveryDto();
////        updateDeliveryDto.setZipCode(Arrays.asList("001", "002"));
////        updateDeliveryDto.setUsername(Arrays.asList("GUROUSER1", "GUROUSER2"));
////
////        System.out.println(adminService.updateDelivery(userDetails, updateDeliveryDto));
////    }
////
////    @DisplayName("get Search")
////    @Test
////    void searchByDetails() {
////        UserDetailsImpl userDetails = getUserDetail();
////
////        SearchReqDto reqDto = new SearchReqDto();
////        reqDto.setUsername("GUROUSER1");
////        reqDto.setRoute("A");
////        reqDto.setState("배송중");
////        reqDto.setSubRoute(Arrays.asList(1, 2, 3));
//////        reqDto.setCurrentDay(3);
////        reqDto.setOption(false);
////
////        for(AdminCourierDto result : adminService.searchByDetails(userDetails, reqDto)){
////            System.out.println(result);
////        }
////    }
////
////    @DisplayName("set State Delay")
////    @Test
////    void setStateDelay() {
////        UserDetailsImpl userDetails = getUserDetail();
////
////        System.out.println(adminService.setStateDelay(userDetails, Arrays.asList(1L, 2L)));
////    }
////
////    @DisplayName("set DeliveryPerson")
////    @Test
////    void setDeliveryPerson() {
////        UserDetailsImpl userDetails = getUserDetail();
////
////        SetDeliveryPersonReqDto reqDto = new SetDeliveryPersonReqDto();
////        reqDto.setCouriers(Arrays.asList(1L, 2L));
////        reqDto.setUsername("GUROUSER1");
////
////        System.out.println(adminService.setDeliveryPerson(userDetails, reqDto));
////    }
////
////    @DisplayName("set Courier")
////    @Test
////    void updateCourier() {
////        UserDetailsImpl userDetails = getUserDetail();
////
////        CourierReqUpdateDto reqUpdateDto = new CourierReqUpdateDto("배송완료", "2022-09-09", "GUROUSER2");
////
////        System.out.println(adminService.updateCourier(1L, userDetails, reqUpdateDto).getMsg());
////    }
//
//    UserDetailsImpl getUserDetail(){
//        Optional<Account> guroadmin = accountRepository.findByUsername("GUROADMIN");
//        return new UserDetailsImpl(guroadmin.get());
//    }
//
//    @Test
//    void autoDelivery(){
//        UserDetailsImpl user = getUserDetail();
//        List<AutoDeliveryDto> autoDeliveryDtos = new ArrayList<>();
//        for(int i=1; i<5; i++)  autoDeliveryDtos.add(new AutoDeliveryDto("GUROUSER"+i, 300));
//
//        adminService.autoDelivery(user, autoDeliveryDtos, "A");
//    }
//}