package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.CourierCountDto;
import com.backendteam5.finalproject.dto.CourierDto;
import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.dto.SearchResponseDto;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Transactional
    public CourierResUpdateDto checkCourierState(Long courierId) {
        LocalDate now = LocalDate.now();
        String curDate = now.format(formatter);

        return courierRepository.findById(courierId)
                .filter(courier -> courier.getState().equals(Courier.State.SHIPPING))
                .map(courier -> {
                    courier.saveUpdate(Courier.State.DELIVERED, courier.getDeliveryAssignment().getAccount().getUsername(), curDate);
                    return new CourierResUpdateDto("운송장 배송완료처리 성공");
                })
                .orElse(new CourierResUpdateDto("해당 운송장상태 변경이 불가능합니다."));
    }

    @Transactional
    public CourierResUpdateDto uncheckCourierState(Long courierId) {
        return courierRepository.findById(courierId)
                .filter(courier -> courier.getState().equals(Courier.State.DELIVERED))
                .map(courier -> {
                    courier.saveUpdate(Courier.State.SHIPPING, "GUROADMIN", "배송전");
                    return new CourierResUpdateDto("배송대기상태로 수정되었습니다.");
                })
                .orElse(new CourierResUpdateDto("해당 운송장은 배송대기중입니다."));
    }

    /*
     * progressCnt : 배송중인 택배의 개수
     * completeCnt : 배송완료한 택배의 개수
     */
    // 택배기사 페이지 택배 배송 상태별 조회.
    @Transactional(readOnly = true)
    public SearchResponseDto searchFilter(UserDetailsImpl userDetails, Long state) {
        LocalDate now = LocalDate.now();
        String curDate = now.format(formatter);

        Long beforeCnt = courierRepository.countPreviousDeliveries(userDetails.getUsername(), Courier.State.DELIVERED);
        log.info("beforeCnt = " + beforeCnt);

        Courier.State status = state == 0 ? Courier.State.SHIPPING : Courier.State.DELIVERED;
        List<CourierDto> courierList = courierRepository.searchByUsernameAndState(userDetails.getUser(), status, "GUROADMIN", curDate);
        CourierCountDto countDto = courierRepository.stateCount(userDetails.getUser(), curDate);

        return new SearchResponseDto(courierList, countDto.getProgressCnt(), countDto.getCompleteCnt(), beforeCnt);
    }


    /*
     * progressCnt : 배송중인 택배의 개수
     * completeCnt : 배송완료한 택배의 개수
     */
    // 택배기사 페이지 customer(수령인)으로 조회
    @Transactional(readOnly = true)
    public SearchResponseDto searchCustomer(UserDetailsImpl userDetails, String customer) {
        LocalDate now = LocalDate.now();
        String curDate = now.format(formatter);

        CourierCountDto countDto = courierRepository.stateCount(userDetails.getUser(), curDate);
        Long progressCnt = countDto.getProgressCnt();
        Long completeCnt = countDto.getCompleteCnt();
        Long beforeCnt = courierRepository.countPreviousDeliveries(userDetails.getUsername(), Courier.State.DELIVERED);

        // 수령인 이름으로 조회.
        List<CourierDto> courList = courierRepository.searchCustomer(customer);
        return new SearchResponseDto(courList, completeCnt, progressCnt, beforeCnt);
    }

    public SearchResponseDto searchComplete(UserDetailsImpl userDetails) {

        LocalDate now = LocalDate.now();
        String curDate = now.format(formatter);

        CourierCountDto countDto = courierRepository.stateCount(userDetails.getUser(), curDate);
        Long progressCnt = countDto.getProgressCnt();
        Long completeCnt = countDto.getCompleteCnt();
        Long beforeCnt = courierRepository.countPreviousDeliveries(userDetails.getUsername(), Courier.State.DELIVERED);

        List<CourierDto> courierList = courierRepository.searchBeforeComplete(userDetails.getUsername(), Courier.State.DELIVERED);

        return new SearchResponseDto(courierList, completeCnt, progressCnt, beforeCnt);
    }
}

