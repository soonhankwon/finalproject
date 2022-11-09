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
import java.util.*;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void createDommie() {

        String route = "A";
        Boolean state = false;
        String customer = "수령인";

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        String arrivalDate = formatter.format(date);
        String username = "region";

        for (int i = 1; i <= 20; i++) {
            String index = Integer.toString(i);

            Courier courier = new Courier(route, i, state, customer + index, arrivalDate, username);
            courierRepository.save(courier);
        }

        for (int i = 21; i <= 40; i++) {
            String index = Integer.toString(i);

            Courier courier2 = new Courier(route, 5, state, customer + index, arrivalDate, username);
            courierRepository.save(courier2);
        }
    }

    @Transactional
    public CourierResUpdateDto updateCourier(Long courierId, UserDetailsImpl userDetails,
                                CourierReqUpdateDto courierReqUpdateDto) {

        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NullPointerException("해당 운송장이 존재하지 않습니다"));

        courier.update(courierReqUpdateDto);
        courierRepository.save(courier);
        return new CourierResUpdateDto("운송장 할당완료");
    }

    @Transactional
    public CourierResUpdateDto updateCourierBySubRoute(int subRouteId, UserDetailsImpl userDetails,
                                          CourierReqUpdateDto courierReqUpdateDto) {
        Account account = accountRepository.findByUsername(courierReqUpdateDto.getUsername())
                .orElseThrow(() -> new NullPointerException("해당 택배기사가 존재하지 않습니다"));

        List<Courier> courier = courierRepository.findBySubRoute(subRouteId);

        for (int i = 0; i < courier.size(); i++) {
            courier.get(i).setUpdate(5, courierReqUpdateDto.getUsername());
            courierRepository.save(courier.get(i));
        }
        return new CourierResUpdateDto("운송장 할당완료");
    }

    @Transactional
    public CourierResUpdateDto checkCourierState(Long courierId, UserDetailsImpl userDetails,
                                                 CourierReqUpdateDto courierReqUpdateDto) {

        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NullPointerException("해당 운송장이 존재하지 않습니다"));

        if (!courier.getState() && courier.getUsername().equals(courierReqUpdateDto.getUsername())) {
            courier.check(courierReqUpdateDto);
            courierRepository.save(courier);
            return new CourierResUpdateDto("배송완료");
        } else {
            return new CourierResUpdateDto("해당 운송장상태 변경이 불가능합니다.");
        }
    }

    @Transactional
    public CourierResUpdateDto uncheckCourierState(Long courierId, UserDetailsImpl userDetails,
                                      CourierReqUpdateDto courierReqUpdateDto) {

        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NullPointerException("해당 운송장이 존재하지 않습니다"));

        if (courier.getState() && courier.getUsername().equals(courierReqUpdateDto.getUsername())) {
            courier.uncheck(courierReqUpdateDto);
            courierRepository.save(courier);
            return new CourierResUpdateDto("배송대기상태로 수정되었습니다.");
        } else {
            return new CourierResUpdateDto("해당 운송장은 배송대기중입니다.");
        }
    }
}
