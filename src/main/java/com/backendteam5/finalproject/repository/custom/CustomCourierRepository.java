package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.CourierDto;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;

import java.util.List;

public interface CustomCourierRepository {

    List<CourierDto> searchByUsernameAndState(Account account, String state, String username);
    Long countUsernameAndState(Account account, String state, String username);
    List<CourierDto> searchCustomer(String customer);

}
