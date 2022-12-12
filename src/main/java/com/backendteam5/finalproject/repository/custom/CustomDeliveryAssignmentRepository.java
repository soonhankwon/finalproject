package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.DeliveryAssignmentDto;

import java.util.List;

public interface CustomDeliveryAssignmentRepository {
    void updateDelivery(List<Long> areaId, Long accountId);
    List<DeliveryAssignmentDto> selectDelivery(String area, String route);
}
