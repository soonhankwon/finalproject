package com.backendteam5.finalproject.service;


import com.backendteam5.finalproject.dto.CourierReqUpdateDto;
import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.repository.AccountRepository;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void createDommie() {

        String area = "A";
        Boolean state = false;
        String customer = "수령인";

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        String arrivalDate = formatter.format(date);
        String username = "region";


        for (int i = 0; i <= 20; i++) {
            String index = Integer.toString(i);

            Courier courier = new Courier(area + index, state, customer + index, arrivalDate, username);
            courierRepository.save(courier);
        }
    }

    @Transactional
    public String updateCourier(Long courierId, UserDetailsImpl userDetails,
                                             CourierReqUpdateDto courierReqUpdateDto) {

        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NullPointerException("해당 운송장이 존재하지 않습니다"));
        courier.update(courierReqUpdateDto);
        courierRepository.save(courier);
        return "할당완료";
    }

    public CourierResUpdateDto checkCourierState(Long courierId, UserDetailsImpl userDetails,
                                                 CourierReqUpdateDto courierReqUpdateDto) {

        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NullPointerException("해당 운송장이 존재하지 않습니다"));

        courierRepository.save(courier);
        return new CourierResUpdateDto();
    }

    public CourierResUpdateDto uncheckCourierState(Long courierId, UserDetailsImpl userDetails,
                                                   CourierReqUpdateDto courierReqUpdateDto) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NullPointerException("해당 운송장이 존재하지 않습니다"));

        courierRepository.save(courier);
        return new CourierResUpdateDto();
    }
}
