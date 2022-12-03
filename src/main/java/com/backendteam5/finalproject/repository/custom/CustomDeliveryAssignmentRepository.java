package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.DeliveryAssignmentDto;

import java.util.List;

public interface CustomDeliveryAssignmentRepository {
    long updateDelivery(String zipCode, String username);

    List<DeliveryAssignmentDto> selectDelivery(String area, String route);
}
