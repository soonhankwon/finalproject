package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.CourierCountDto;
import com.backendteam5.finalproject.dto.CourierDto;
import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.dto.SearchResponseDto;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;

    @Transactional
    public CourierResUpdateDto checkCourierState(Long courierId) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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

        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 포맷 적용
        String curDate = now.format(formatter);

        Courier.State status;
        CourierCountDto countDto = courierRepository.stateCount(userDetails.getUser(), curDate);
        Long progressCnt = countDto.getProgressCnt();
        Long completeCnt = countDto.getCompleteCnt();
        Long beforeCnt = courierRepository.countTest(userDetails.getUsername(), Courier.State.DELIVERED);
        System.out.println("beforeCnt = " + beforeCnt);

        // state == 0 배송중 조회.
        if (state == 0) {
            status = Courier.State.SHIPPING;
            List<CourierDto> courierList = courierRepository.searchByUsernameAndState(userDetails.getUser(), status, "GUROADMIN", curDate);
            return new SearchResponseDto(courierList, completeCnt, progressCnt, beforeCnt);
        };
        status = Courier.State.DELIVERED;
        // state == 1 배송 완료 조회.
        List<CourierDto> courierList = courierRepository.searchByUsernameAndState(userDetails.getUser(), status, userDetails.getUsername(), curDate);
        return new SearchResponseDto(courierList, completeCnt, progressCnt, beforeCnt);
    }

    /*
     * progressCnt : 배송중인 택배의 개수
     * completeCnt : 배송완료한 택배의 개수
     */
    // 택배기사 페이지 customer(수령인)으로 조회
    @Transactional(readOnly = true)
    public SearchResponseDto searchCustomer(UserDetailsImpl userDetails, String customer) {

        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 포맷 적용
        String curDate = now.format(formatter);

        CourierCountDto countDto = courierRepository.stateCount(userDetails.getUser(), curDate);
        Long progressCnt = countDto.getProgressCnt();
        Long completeCnt = countDto.getCompleteCnt();
        Long beforeCnt = courierRepository.countTest(userDetails.getUsername(), Courier.State.DELIVERED);

        // 수령인 이름으로 조회.
        List<CourierDto> courList = courierRepository.searchCustomer(customer);
        return new SearchResponseDto(courList, completeCnt, progressCnt, beforeCnt);
    }

    public SearchResponseDto searchComplete(UserDetailsImpl userDetails) {

        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 포맷 적용
        String curDate = now.format(formatter);

        CourierCountDto countDto = courierRepository.stateCount(userDetails.getUser(), curDate);
        Long progressCnt = countDto.getProgressCnt();
        Long completeCnt = countDto.getCompleteCnt();
        Long beforeCnt = courierRepository.countTest(userDetails.getUsername(), Courier.State.DELIVERED);

        List<CourierDto> courierList = courierRepository.searchBeforeComplete(userDetails.getUsername(), Courier.State.DELIVERED);

        return new SearchResponseDto(courierList, completeCnt, progressCnt, beforeCnt);
    }
}

