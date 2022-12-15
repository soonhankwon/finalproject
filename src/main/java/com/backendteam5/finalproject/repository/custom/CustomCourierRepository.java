package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.*;
import com.backendteam5.finalproject.entity.Account;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomCourierRepository {

    List<CourierDto> searchByUsernameAndState(Account account, String state, String username, String curDate);
    CourierCountDto stateCount(Account account, String curDate);
    List<CourierDto> searchCustomer(String customer);

    List<CourierDto> searchBeforeComplete(String username, String state);
    Long countTest(String username, String state);

    void updateByCourierId(Long courierId, String deliveryPerson);
    List<RouteCountDto> countRouteState(String area);
    List<AdminCourierDto> searchByDetail(String username, String area, SearchReqDto searchReqDto);
    List<AdminCourierDto> searchByCouriers(List<Long> couriers);


    String setUpdateStateDelay(List<Long> couriers);
    String setDeliveryPerson(List<Long> couriers, String username);

    String setReady();

    String getNowDate();
}
