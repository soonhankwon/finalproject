package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.CourierReqUpdateDto;
import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.repository.AccountRepository;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.repository.DeliveryAssignmentRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final AccountRepository accountRepository;
    private final CourierRepository courierRepository;
    private final DeliveryAssignmentRepository deliveryAssignmentRepository;


    @Transactional
    public CourierResUpdateDto updateCourier(Long courierId, UserDetailsImpl userDetails,
                                             CourierReqUpdateDto courierReqUpdateDto) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NullPointerException("해당 운송장이 존재하지 않습니다"));
        courier.update(courierReqUpdateDto);
        courierRepository.save(courier);
        return new CourierResUpdateDto("운송장 할당완료");
    }
}
