package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.CountUserDto;
import com.backendteam5.finalproject.dto.DeliveryAssignmentDto;

import java.util.List;

public interface CustomDeliveryAssignmentRepository {
    void updateDelivery(List<Long> areaId, Long accountId);

    // Delivery를 수정을 위해서 지역별로 조회하는 메소드(Area와 Route가 필요함)
    // Area는 Context의 account에 등록된 Area를 조회하여 넣을거임
    List<DeliveryAssignmentDto> selectDelivery(String area, String route, String def);

    List<CountUserDto> findByTempCount(String area, String def);
    List<CountUserDto> findByDirectCount(String area, String def);
}
