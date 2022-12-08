package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.AdminCourierDto;
import com.backendteam5.finalproject.dto.RouteCountDto;
import com.backendteam5.finalproject.dto.SearchReqDto;
import com.backendteam5.finalproject.repository.CourierRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
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

    @DisplayName("조건 검색에 대한 테스트")
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
        for(AdminCourierDto result : courierRepository.searchByDetail("GUROADMIN","구로구",searchReqDto)
        ){
            System.out.println(result);
        }
    }

    @DisplayName("courier의 날짜 업데이트와 deliveryPerosn 초기화 => 쿼리 실행하기")
    @Test
    void setReset(){
        System.out.println(courierRepository.setReady());
    }

    @DisplayName("배송지연 상태 업데이트 테스트")
    @Test
    void setUpdateState(){
        List<Long> couriers = Arrays.asList(1L, 2L);
        System.out.println(courierRepository.setUpdateStateDelay(couriers));
    }

    @DisplayName("직접 할당에 대한 테스트")
    @Test
    void setDeliveryPerson(){
        List<Long> couriers = Arrays.asList(1L, 2L);
        System.out.println(courierRepository.setDeliveryPerson(couriers, "GUROUSER2"));
    }
}