package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.RouteCountDto;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.repository.DeliveryAssignmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomCourierRepositoryTest_Gubeom {

    @Autowired
    private CourierRepository courierRepository;

    @DisplayName("각 Route마다의 state에 따른 Courier 갯수")
    @Test
    void countRouteState() {
        List<RouteCountDto> results = courierRepository.countRouteState("구로구", convertNowDate());
        for(RouteCountDto result : results){
            System.out.println(result);
        };
    }

    private String convertNowDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }
}