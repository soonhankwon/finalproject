package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.CourierDto;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;

import java.util.List;

public interface CustomCourierRepository {

    List<CourierDto> searchByUsernameAndState(String username, String state);
    Long countUsernameAndState(String username, String state);
    List<CourierDto> searchCustomer(String customer);

    List<CourierDto> test(Account account);

}
