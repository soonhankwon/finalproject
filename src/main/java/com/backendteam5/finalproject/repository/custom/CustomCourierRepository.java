package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.CountDirectDto;
import com.backendteam5.finalproject.dto.CourierDto;
import com.backendteam5.finalproject.dto.RouteCountDto;
import com.backendteam5.finalproject.entity.Account;

import java.util.List;

public interface CustomCourierRepository {

    List<CourierDto> searchByUsernameAndState(Account account, String state, String username);
    Long countUsernameAndState(Account account, String state, String username);
    List<CourierDto> searchCustomer(String customer);
    List<RouteCountDto> countRouteState(String area, String date);

    List<CountDirectDto> countUsernameDirect(Account account, String date);
    Long countUsernameTemp(Account account, String date);
//    List<CourierDto> searchByRouteAndSubRoute(String route, List<String> subRoute);
//    List<CourierDto> searchBy
    void updateByCourierId(Long courierId, String deliveryPerson);
}
