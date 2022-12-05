package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.CourierDto;
import com.backendteam5.finalproject.entity.Account;

import java.util.List;

public interface CustomCourierRepository {

    List<CourierDto> searchByUsernameAndState(Account account, String state);
    Long countUsernameAndState(Account account, String state);
    List<CourierDto> searchCustomer(String customer);
    void updateByCourierId(Long courierId, String deliveryPerson);
}

