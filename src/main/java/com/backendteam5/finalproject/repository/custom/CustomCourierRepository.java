package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.*;
import com.backendteam5.finalproject.entity.Account;

import java.util.List;

public interface CustomCourierRepository {

    List<CourierDto> searchByUsernameAndState(Account account, String state);
    Long countUsernameAndState(Account account, String state);
    List<CourierDto> searchCustomer(String customer);


    List<RouteCountDto> countRouteState(String area);
    List<CountDirectDto> countUsernameDirect(Account account);
    Long countUsernameTemp(Account account);
    List<AdminCourierDto> searchByDetail(String username,String area, SearchReqDto searchReqDto);

    String setReady();
    String setUpdateStateDelay(List<Long> couriers);
    String setDeliveryPerson(List<Long> couriers, String username);

    String getNowDate();
}
