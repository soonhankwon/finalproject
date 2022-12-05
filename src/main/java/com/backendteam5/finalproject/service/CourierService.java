package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.CourierDto;
import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.dto.SearchResponseDto;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.AreaIndex;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.entity.DeliveryAssignment;
import com.backendteam5.finalproject.repository.AccountRepository;
import com.backendteam5.finalproject.repository.AreaIndexRepository;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.repository.DeliveryAssignmentRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;
    private final AreaIndexRepository areaIndexRepository;
    private final AccountRepository accountRepository;
    private final DeliveryAssignmentRepository deliveryAssignmentRepository;



    @Transactional
    public CourierResUpdateDto checkCourierState(Long courierId) {

        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NullPointerException("해당 운송장이 존재하지 않습니다"));

        if (courier.getState().equals("배송중")) {
            courier.saveUpdate("배송완료", courier.getDeliveryAssignment().getAccount().getUsername());
            return new CourierResUpdateDto("배송완료");
        } else {
            return new CourierResUpdateDto("해당 운송장상태 변경이 불가능합니다.");
        }
    }

    @Transactional
    public CourierResUpdateDto uncheckCourierState(Long courierId) {

        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NullPointerException("해당 운송장이 존재하지 않습니다"));

        if (courier.getState().equals("배송완료")) {
            courier.saveUpdate("배송중", "GUROADMIN");
            return new CourierResUpdateDto("배송대기상태로 수정되었습니다.");
        } else {
            return new CourierResUpdateDto("해당 운송장은 배송대기중입니다.");
        }
    }

    // 택배기사 페이지 택배 배송 상태별 조회.
    @Transactional(readOnly = true)
    public SearchResponseDto searchFilter(UserDetailsImpl userDetails, Long state) {

        /*
         * progressCnt : 배송중인 택배의 개수
         * completeCnt : 배송완료한 택배의 개수
         */
        String status = "배송중";
        Long progressCnt = courierRepository.countUsernameAndState(userDetails.getUser(), status, "GUROADMIN");
        status = "배송완료";
        Long completeCnt = courierRepository.countUsernameAndState(userDetails.getUser(), status, userDetails.getUsername());
        // state == 0 배송중 조회.
        if (state == 0) {
            status = "배송중";
            List<CourierDto> courierList = courierRepository.searchByUsernameAndState(userDetails.getUser(), status, "GUROADMIN");
            return new SearchResponseDto(courierList, completeCnt, progressCnt);

        }

        // state == 1 배송 완료 조회.
        List<CourierDto> courierList = courierRepository.searchByUsernameAndState(userDetails.getUser(), status, userDetails.getUsername());
        return new SearchResponseDto(courierList, completeCnt, progressCnt);
    }

    // 택배기사 페이지 customer(수령인)으로 조회
    @Transactional(readOnly = true)
    public SearchResponseDto searchCustomer(UserDetailsImpl userDetails, String customer) {

        /*
         * progressCnt : 배송중인 택배의 개수
         * completeCnt : 배송완료한 택배의 개수
         */
        String status = "배송완료";
        Long completeCnt = courierRepository.countUsernameAndState(userDetails.getUser(), status, userDetails.getUsername());
        status = "배송중";
        Long progressCnt = courierRepository.countUsernameAndState(userDetails.getUser(), status, "GUROADMIN");

        // 수령인 이름으로 조회.
        List<CourierDto> courList = courierRepository.searchCustomer(customer);

        return new SearchResponseDto(courList, completeCnt, progressCnt);
    }
}
