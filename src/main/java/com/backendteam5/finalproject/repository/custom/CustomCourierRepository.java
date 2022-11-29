package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.CourierDto;

import java.util.List;

public interface CustomCourierRepository {

    List<CourierDto> searchByUsernameAndState(String username, Boolean state);

    Long countUsernameAndState(String username, Boolean state);

    List<CourierDto> searchCustomer(String customer);

    void updateByCourierId(Long courierId, String username);
}
