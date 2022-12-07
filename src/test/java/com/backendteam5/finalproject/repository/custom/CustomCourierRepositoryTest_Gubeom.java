package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.AdminCourierDto;
import com.backendteam5.finalproject.dto.RouteCountDto;
import com.backendteam5.finalproject.dto.SearchReqDto;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.repository.DeliveryAssignmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class CustomCourierRepositoryTest_Gubeom {

    @Autowired
    private CourierRepository courierRepository;

    @DisplayName("각 Route마다의 state에 따른 Courier 갯수")
    @Test
    void countRouteState() {
        List<RouteCountDto> results = courierRepository.countRouteState("구로구");
        for(RouteCountDto result : results){
            System.out.println(result);
        };
    }

    private String convertNowDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    @Test
    void searchByDetail(){
        List<Integer> subroute = Arrays.asList(1,2,3);
        SearchReqDto searchReqDto = new SearchReqDto();
        searchReqDto.setRoute("A");
        searchReqDto.setState("배송중");
        searchReqDto.setSubRoute(subroute);
        searchReqDto.setUsername("GUROUSER1");
        searchReqDto.setCurrentDay(2);
        searchReqDto.setOption(false);
        for(AdminCourierDto result : courierRepository.searchByDetail("구로구",searchReqDto)
        ){
            System.out.println(result);
        }
    }

    @Test
    void setArrivalDate(){
        List<String> zipCode = Arrays.asList("001", "002");
        System.out.println(courierRepository.setArrivalDate(zipCode));
    }

    @Test
    void setUpdateState(){
        List<Long> couriers = Arrays.asList(1L, 2L);
        System.out.println(courierRepository.setUpdateState(couriers));
    }

    @Test
    void setDeliveryPerson(){
        List<Long> couriers = Arrays.asList(1L, 2L);
        System.out.println(courierRepository.setDeliveryPerson(couriers, "GUROUSER2"));
    }
}