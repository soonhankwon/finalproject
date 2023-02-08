package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.*;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;

import java.util.List;

public interface CustomCourierRepository {

    List<CourierDto> searchBeforeComplete(String username, Courier.State state);
    Long countPreviousDeliveries(String username, Courier.State state);
    List<CourierDto> searchByUsernameAndState(Account account, Courier.State state, String username, String curDate);
    CourierCountDto stateCount(Account account, String curDate);
    List<CourierDto> searchCustomer(String customer);
    List<RouteCountDto> countRouteState(String area);
    List<AdminCourierDto> searchByDetail(String username, String area, SearchReqDto searchReqDto);
    List<AdminCourierDto> searchByCouriers(List<Long> couriers);
    String setUpdateStateDelay(List<Long> couriers);
    String setDeliveryPerson(List<Long> couriers, String username);
    String setReady();
    String getNowDate();
}
