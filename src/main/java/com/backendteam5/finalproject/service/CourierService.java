package com.backendteam5.finalproject.service;


import com.backendteam5.finalproject.dto.CourierReqUpdateDto;
import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.dto.SearchResponseDto;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.entity.UserRoleEnum;
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

        String arrivalDate = formatter.format(date);
//        String username = accountRepository.findByRouteAndRole(route, UserRoleEnum.ADMIN).get(0).getUsername();
        String username = "dlwotjs";
        for (int i = 1; i <= 20; i++) {
            String index = Integer.toString(i);

            Courier courier = new Courier(route, i, state, customer + index, arrivalDate, username);
            courierRepository.save(courier);
        }
        state = true;
        for (int i = 21; i <= 40; i++) {
            String index = Integer.toString(i);

            Courier courier2 = new Courier(route, 5, state, customer + index, arrivalDate, username);
            courierRepository.save(courier2);
        }
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

    public SearchResponseDto searchFilter(UserDetailsImpl userDetails, Long state) {
        Boolean status = false;
        if (state == 1) {
            status = true;
            List<Courier> courierList = courierRepository.findByUsernameAndStateOrderByArrivalDateDesc(userDetails.getUsername(), status);
            Long completeCnt = courierRepository.countByUsernameAndState(userDetails.getUsername(), status);
            status = false;
            Long progressCnt = courierRepository.countByUsernameAndState(userDetails.getUsername(), status);
            return new SearchResponseDto(courierList, completeCnt, progressCnt);

        }
        List<Courier> courierList = courierRepository.findByUsernameAndStateOrderByArrivalDateDesc(userDetails.getUsername(), status);
        status = true;
        Long completeCnt = courierRepository.countByUsernameAndState(userDetails.getUsername(), status);
        status = false;
        Long progressCnt = courierRepository.countByUsernameAndState(userDetails.getUsername(), status);
        return new SearchResponseDto(courierList, completeCnt, progressCnt);
    }

    public SearchResponseDto searchCustomer(UserDetailsImpl userDetails, String customer) {
        Boolean status;
        status = true;
        Long completeCnt = courierRepository.countByUsernameAndState(userDetails.getUsername(), status);
        status = false;
        Long progressCnt = courierRepository.countByUsernameAndState(userDetails.getUsername(), status);
        List<Courier> courList = courierRepository.findByCustomer(customer);

        return new SearchResponseDto(courList, completeCnt, progressCnt);

    }
}
