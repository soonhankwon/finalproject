package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.CourierDto;
import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.dto.SearchResponseDto;
import com.backendteam5.finalproject.dto.SignupRequestDto;
import com.backendteam5.finalproject.entity.*;
import com.backendteam5.finalproject.repository.AccountRepository;
import com.backendteam5.finalproject.repository.AreaIndexRepository;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.repository.DeliveryAssignmentRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;
    private final AreaIndexRepository areaIndexRepository;
    private final AccountRepository accountRepository;
    private final DeliveryAssignmentRepository deliveryAssignmentRepository;


    @Transactional
    public void createDommie() {



        String state = "배송중";
        String customer = "수령인";

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

        String arrivalDate = formatter.format(date);
        String username = "dlwotjs";
        char newRoute = 'A';
//        for (int i = 0; i <= 20; i++) {
//            for (int j = 1; j <= 10; j++) {
//                areaIndexRepository.save(
//                        new AreaIndex("구로구", String.valueOf((char) (newRoute + i)), j, "집코드")
//                );
//            }
//        }


        // route : A, subRoute : 1, zipCode : 집코드
        Optional<AreaIndex> optionalAreaIndex = areaIndexRepository.findById(1L);
        AreaIndex areaIndex = optionalAreaIndex.get();

//        // route : A, subRoute : 2, zipCode : 집코드
//        Optional<AreaIndex> optionalAreaIndex1 = areaIndexRepository.findById(2L);
//        AreaIndex areaIndex1 = optionalAreaIndex1.get();

        // account 만들기
        Optional<Account> optionalAccount = accountRepository.findById(1L);
        Account account = optionalAccount.get();

        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setUsername("택배기사2");
        requestDto.setPassword("비밀번호");
        requestDto.setArea("구로구");

        UserRoleEnum role = UserRoleEnum.USER;

        accountRepository.save(new Account(requestDto, role));

        Optional<Account> optionalAccount1 = accountRepository.findById(2L);
        Account account1 = optionalAccount1.get();

        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
//        accountList.add(account1);

//        List<Account> accountList2 = new ArrayList<>();
//        accountList.add(account);

        // DeliveryAssignment 객체 만들기
        DeliveryAssignment assignment = deliveryAssignmentRepository.save(new DeliveryAssignment(1L, areaIndex, accountList));
//        DeliveryAssignment assignment1 = deliveryAssignmentRepository.save(new DeliveryAssignment(2L, areaIndex1, accountList2));


        for (int i = 1; i <= 20; i++) {
            String index = Integer.toString(i);
            if (i < 10) {
                Courier courier = new Courier(areaIndex, state, customer + index, arrivalDate, assignment);
                courierRepository.save(courier);
            }
            else {
                if (i == 11){
                    accountList.remove(account);
                    accountList.add(account1);
                    assignment = deliveryAssignmentRepository.save(new DeliveryAssignment(2L, areaIndex, accountList));
                }
                Courier courier = new Courier(areaIndex, state, customer + index, arrivalDate, assignment);
                courierRepository.save(courier);


            }
        }

//        for (int i = 21; i <= 40; i++) {
//            String index = Integer.toString(i);
//            Courier courier = new Courier(areaIndex1, state, customer+index, arrivalDate, assignment1);
//            courierRepository.save(courier);
//        }
    }

//    @Transactional
//    public CourierResUpdateDto checkCourierState(Long courierId) {
//
//        Courier courier = courierRepository.findById(courierId)
//                .orElseThrow(() -> new NullPointerException("해당 운송장이 존재하지 않습니다"));
//
//        if (!courier.getState()) {
//            courier.setState(true);
//            return new CourierResUpdateDto("배송완료");
//        } else {
//            return new CourierResUpdateDto("해당 운송장상태 변경이 불가능합니다.");
//        }
//    }
//
//    @Transactional
//    public CourierResUpdateDto uncheckCourierState(Long courierId) {
//
//        Courier courier = courierRepository.findById(courierId)
//                .orElseThrow(() -> new NullPointerException("해당 운송장이 존재하지 않습니다"));
//
//        if (courier.getState()) {
//            courier.setState(false);
//            return new CourierResUpdateDto("배송대기상태로 수정되었습니다.");
//        } else {
//            return new CourierResUpdateDto("해당 운송장은 배송대기중입니다.");
//        }
//    }

    // 택배기사 페이지 택배 배송 상태별 조회.
    @Transactional(readOnly = true)
    public SearchResponseDto searchFilter(UserDetailsImpl userDetails, Long state) {

        /*
         * progressCnt : 배송중인 택배의 개수
         * completeCnt : 배송완료한 택배의 개수
         */
        String status = "배송중";
        Long progressCnt = courierRepository.countUsernameAndState(userDetails.getUsername(), status);
        status = "배송완료";
        Long completeCnt = courierRepository.countUsernameAndState(userDetails.getUsername(), status);
        // state == 0 배송중 조회.
        if (state == 0) {
            status = "배송중";
            List<CourierDto> courierList = courierRepository.searchByUsernameAndState(userDetails.getUsername(), status);
            return new SearchResponseDto(courierList, completeCnt, progressCnt);

        }

        // state == 1 배송 완료 조회.
        List<CourierDto> courierList = courierRepository.searchByUsernameAndState(userDetails.getUsername(), status);
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
        Long completeCnt = courierRepository.countUsernameAndState(userDetails.getUsername(), status);
        status = "배송중";
        Long progressCnt = courierRepository.countUsernameAndState(userDetails.getUsername(), status);

        // 수령인 이름으로 조회.
        List<CourierDto> courList = courierRepository.searchCustomer(customer);

        return new SearchResponseDto(courList, completeCnt, progressCnt);
    }

    public List<CourierDto> test(UserDetailsImpl userDetails) {
        Optional<Account> byUsername = accountRepository.findByUsername(userDetails.getUsername());
        Account account = byUsername.get();


        return courierRepository.test(account);
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
    }
}
