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
            courier.saveUpdate("배송중", "ADMIN");
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
        Long progressCnt = courierRepository.countUsernameAndState(userDetails.getUser(), status);
        status = "배송완료";
        Long completeCnt = courierRepository.countUsernameAndState(userDetails.getUser(), status);
        // state == 0 배송중 조회.
        if (state == 0) {
            status = "배송중";
            List<CourierDto> courierList = courierRepository.searchByUsernameAndState(userDetails.getUser(), status);
            return new SearchResponseDto(courierList, completeCnt, progressCnt);

        }

        // state == 1 배송 완료 조회.
        List<CourierDto> courierList = courierRepository.searchByUsernameAndState(userDetails.getUser(), status);
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
        Long completeCnt = courierRepository.countUsernameAndState(userDetails.getUser(), status);
        status = "배송중";
        Long progressCnt = courierRepository.countUsernameAndState(userDetails.getUser(), status);

        // 수령인 이름으로 조회.
        List<CourierDto> courList = courierRepository.searchCustomer(customer);

        return new SearchResponseDto(courList, completeCnt, progressCnt);
    }



    @Transactional
    public void test3() {

        char newRoute = 'A';
        for (int i = 0; i <= 20; i++) {
            for (int j = 1; j <= 10; j++) {
                areaIndexRepository.save(
                        new AreaIndex("구로구", String.valueOf((char) (newRoute + i)), j, "집코드")
                );
            }
        }

        // route : A, subRoute : 1, zipCode : 집코드
        Optional<AreaIndex> optionalAreaIndex = areaIndexRepository.findById(1L);
        AreaIndex areaIndex = optionalAreaIndex.get();


        // route : A, subRoute : 2, zipCode : 집코드
        Optional<AreaIndex> optionalAreaIndex1 = areaIndexRepository.findById(2L);
        AreaIndex areaIndex1 = optionalAreaIndex1.get();



        // account 만들기
        Optional<Account> optionalAccount = accountRepository.findById(1L);
        Account account = optionalAccount.get();

        deliveryAssignmentRepository.save(new DeliveryAssignment(account, areaIndex));
        deliveryAssignmentRepository.save(new DeliveryAssignment(account, areaIndex1));

        Optional<DeliveryAssignment> optionalDeliveryAssignment = deliveryAssignmentRepository.findById(1L);
        DeliveryAssignment assignment = optionalDeliveryAssignment.get();

        Optional<DeliveryAssignment> optionalDeliveryAssignment1 = deliveryAssignmentRepository.findById(2L);
        DeliveryAssignment assignment1 = optionalDeliveryAssignment1.get();

        String state;
        String customer = "수령인";
        String arrivalDate = "2022-12-01";
        String registerDate = "2022-12-03";
        for (int i = 0; i < 20;i++) {
            String index = Integer.toString(i);
            if (i < 10) {
                state = "배송중";
                courierRepository.save(
                        new Courier(state, customer + index, arrivalDate, registerDate, 3.421, 3.123, assignment)
                );
            }
            else {
                state = "배송완료";
                courierRepository.save(
                        new Courier(state, customer + index, arrivalDate, registerDate,3.421, 3.123, assignment1)
                );
            }
        }
    }
}