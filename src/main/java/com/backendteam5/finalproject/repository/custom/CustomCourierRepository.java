package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.CountDirectDto;
import com.backendteam5.finalproject.dto.CourierDto;
import com.backendteam5.finalproject.dto.RouteCountDto;
import com.backendteam5.finalproject.entity.Account;

import java.util.List;

public interface CustomCourierRepository {

    List<CourierDto> searchByUsernameAndState(Account account, String state);
    Long countUsernameAndState(Account account, String state);
    List<CourierDto> searchCustomer(String customer);
    List<RouteCountDto> countRouteState(String area, String date);

    List<CountDirectDto> countUsernameDirect(Account account, String date);
    Long countUsernameTemp(Account account, String date);
//    List<CourierDto> searchByRouteAndSubRoute(String route, List<String> subRoute);
//    List<CourierDto> searchBy
}
