package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.CourierReqUpdateDto;
import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.dto.UpdateReqDto;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.repository.AccountRepository;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final AccountRepository accountRepository;
    private final CourierRepository courierRepository;

    @Transactional
    public CourierResUpdateDto updateCourier(Long courierId, UserDetailsImpl userDetails,
                                             CourierReqUpdateDto courierReqUpdateDto) {

        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NullPointerException("해당 운송장이 존재하지 않습니다."));

        Account account = accountRepository.findByUsername(courierReqUpdateDto.getDeliveryPerson())
                .orElseThrow(() -> new IllegalArgumentException("해당 계정이 존재하지 않습니다."));
        courier.update(courierReqUpdateDto);
        courierRepository.save(courier);

        return new CourierResUpdateDto("운송장 상태 변경완료");
    }


    @Transactional
    public CourierResUpdateDto updateCouriers(UpdateReqDto updateReqDto, UserDetailsImpl userDetails) {

        if (updateReqDto.getDeliveryPerson().size() == 1) {
            Account account = accountRepository.findByUsername(updateReqDto.getDeliveryPerson().get(0))
                    .orElseThrow(() -> new NullPointerException("해당 계정이 존재하지 않습니다."));

            for (int i = 0; i < updateReqDto.getCourierIds().size(); i++) {
                courierRepository.updateByCourierId(updateReqDto.getCourierIds().get(i), account.getUsername());
            }
            return new CourierResUpdateDto("운송장 할당완료");
        } else {
            return new CourierResUpdateDto("운송장 할당불가능");
        }
    }
}
