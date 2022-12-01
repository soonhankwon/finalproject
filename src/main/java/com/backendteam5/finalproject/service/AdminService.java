package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.*;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.entity.DeliveryAssignment;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.AccountRepository;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.repository.DeliveryAssignmentRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final AccountRepository accountRepository;
    private final CourierRepository courierRepository;

    //    private final DeliveryAssignmentRepository deliveryAssignmentRepository;
//
//    @Transactional(readOnly = true)
//    public AdminMainResDto findAll(UserDetailsImpl userDetails) {
//        /**
//         * list를 하나더 추가함.
//         * 해당하는 유저네임으로 카운트를 알아내고싶다.
//         */
//
//        String route = userDetails.getUser().getRoute();
//        List<Account> accountList = new LinkedList<>();
//        List<Integer> counts = new LinkedList<>();
//
//        accountList.add(userDetails.getUser());
//        accountList.addAll(accountRepository.findByRouteAndRole(route, UserRoleEnum.USER));
//
//        for (Account account : accountList) counts.add(courierRepository.countCourierByUsername(account.getUsername()));
//
//        return new AdminMainResDto(accountList, null, counts);
//    }
//
//    @Transactional(readOnly = true)
//    public AdminMainResDto searchCourier(Long courierId, UserDetailsImpl userDetails) {
//        List<Account> accountList = new LinkedList<>();
//        accountList.add(userDetails.getUser());
//        Courier courier = courierRepository.findById(courierId).orElseThrow(
//                () -> new IllegalArgumentException("해당 courier가 존재하지 않습니다.")
//        );
//        if (!courier.getDeliveryAssignment().getUsername().equals(userDetails.getUsername())) {
//            List<Account> accounts = accountRepository.findByUsernameStartingWith(courier.getDeliveryAssignment().getUsername());
//            if (accounts.isEmpty()) throw new IllegalArgumentException("해당 배정자가 존재하지 않습니다.");
//            accountList.addAll(accounts);
//        }
//        return new AdminMainResDto(accountList, Collections.singletonList(courier), null);
//    }
//
//
//    @Transactional(readOnly = true)
//    public AdminMainResDto sortedCourier(String username, List<Integer> subRoute,
//                                         int state, Boolean arri, UserDetailsImpl userDetails) {
//        List<Courier> courierList = new LinkedList<>();
//        Account account = userDetails.getUser();
//
//        if (username.isEmpty()) {
//            if (subRoute.isEmpty()) {
//                if (state == 2) {
//                    courierList = arri ? courierRepository.findByRouteOrderByArrivalDateAsc(account.getRoute()) :
//                            courierRepository.findByRouteOrderByArrivalDateDesc(account.getRoute());
//                } else if (state == 1) {
//                    courierList = arri ? courierRepository.findByRouteAndStateOrderByArrivalDateAsc(account.getRoute(), true) :
//                            courierRepository.findByRouteAndStateOrderByArrivalDateDesc(account.getRoute(), true);
//                } else {
//                    courierList = arri ? courierRepository.findByRouteAndStateOrderByArrivalDateAsc(account.getRoute(), false) :
//                            courierRepository.findByRouteAndStateOrderByArrivalDateDesc(account.getRoute(), false);
//                }
//            } else {
//                if (state == 2) {
//                    for (int subroute : subRoute) {
//                        courierList.addAll(arri ? courierRepository.findByRouteAndSubRouteOrderByArrivalDateAsc(account.getRoute(), subroute) :
//                                courierRepository.findByRouteAndSubRouteOrderByArrivalDateDesc(account.getRoute(), subroute));
//                    }
//                } else if (state == 1) {
//                    for (int subroute : subRoute) {
//                        courierList.addAll(arri ? courierRepository.findByRouteAndSubRouteAndStateOrderByArrivalDateAsc(account.getRoute(), subroute, true) :
//                                courierRepository.findByRouteAndSubRouteAndStateOrderByArrivalDateDesc(account.getRoute(), subroute, true));
//                    }
//                } else {
//                    for (int subroute : subRoute) {
//                        courierList.addAll(arri ? courierRepository.findByRouteAndSubRouteAndStateOrderByArrivalDateAsc(account.getRoute(), subroute, false) :
//                                courierRepository.findByRouteAndSubRouteAndStateOrderByArrivalDateDesc(account.getRoute(), subroute, false));
//                    }
//                }
//            }
//        } else {
//            if (subRoute.isEmpty()) {
//                if (state == 2) {
//                    courierList = arri ? courierRepository.findByRouteAndUsernameOrderByArrivalDateAsc(account.getRoute(), username) :
//                            courierRepository.findByRouteAndUsernameOrderByArrivalDateDesc(account.getRoute(), username);
//                } else if (state == 1) {
//                    courierList = arri ? courierRepository.findByRouteAndUsernameAndStateOrderByArrivalDateAsc(account.getRoute(), username, true) :
//                            courierRepository.findByRouteAndUsernameAndStateOrderByArrivalDateDesc(account.getRoute(), username, true);
//                } else {
//                    courierList = arri ? courierRepository.findByRouteAndUsernameAndStateOrderByArrivalDateAsc(account.getRoute(), username, false) :
//                            courierRepository.findByRouteAndUsernameAndStateOrderByArrivalDateDesc(account.getRoute(), username, false);
//                }
//            } else {
//                if (state == 2) {
//                    for (int subroute : subRoute) {
//                        courierList.addAll(arri ? courierRepository.findByRouteAndUsernameAndSubRouteOrderByArrivalDateAsc(account.getRoute(), username, subroute) :
//                                courierRepository.findByRouteAndUsernameAndSubRouteOrderByArrivalDateDesc(account.getRoute(), username, subroute));
//                    }
//                } else if (state == 1) {
//                    for (int subroute : subRoute) {
//                        courierList.addAll(arri ? courierRepository.findByRouteAndUsernameAndSubRouteAndStateOrderByArrivalDateAsc(account.getRoute(), username, subroute, true) :
//                                courierRepository.findByRouteAndUsernameAndSubRouteAndStateOrderByArrivalDateDesc(account.getRoute(), username, subroute, true));
//                    }
//                } else {
//                    for (int subroute : subRoute) {
//                        courierList.addAll(arri ? courierRepository.findByRouteAndUsernameAndSubRouteAndStateOrderByArrivalDateAsc(account.getRoute(), username, subroute, false) :
//                                courierRepository.findByRouteAndUsernameAndSubRouteAndStateOrderByArrivalDateDesc(account.getRoute(), username, subroute, false));
//                    }
//                }
//            }
//        }
//        return new AdminMainResDto(checkuser(account, username), courierList, null);
//    }
//
//    public List<Account> checkuser(Account account, String username) {
//        List<Account> accountList = new LinkedList<>();
//        accountList.add(account);
//        if (username.isEmpty()) {
//            accountList.addAll(accountRepository.findByRouteAndRole(account.getRoute(), UserRoleEnum.USER));
//        } else if (!account.getUsername().equals(username)) {
//            List<Account> accounts = accountRepository.findByUsernameStartingWith(username);
//            if (accounts.isEmpty()) throw new IllegalArgumentException("해당 배정자가 존재하지 않습니다.");
//            accountList.addAll(accounts);
//        }
//        return accountList;
//    }
//
    @Transactional
    public CourierResUpdateDto updateCourier(Long courierId, UserDetailsImpl userDetails,
                                             CourierReqUpdateDto courierReqUpdateDto) {

        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NullPointerException("해당 운송장이 존재하지 않습니다."));

        Account account = accountRepository.findByUsername(courierReqUpdateDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당 계정이 존재하지 않습니다."));
        courier.update(courierReqUpdateDto);
        courierRepository.save(courier);

        return new CourierResUpdateDto("운송장 상태 변경완료");
    }


    @Transactional
    public CourierResUpdateDto updateCouriers(UpdateReqDto updateReqDto, UserDetailsImpl userDetails) {

        if (updateReqDto.getUsernames().size() == 1) {
            Account account = accountRepository.findByUsername(updateReqDto.getUsernames().get(0))
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
//
//
//    @Transactional
//    public CourierResUpdateDto updateCourierByAllUserBySubRoute(UpdateReqDto updateReqDto, UserDetailsImpl
//            userDetails) {
//
//        for (int i = 0; i < updateReqDto.getUsernames().size(); i++) {
//            DeliveryAssignment deliveryAssignment = deliveryAssignmentRepository.findByUsername(updateReqDto.getUsernames().get(i));
//            if(deliveryAssignment.getUsername().isEmpty()) throw new IllegalArgumentException("배정자가 존재하지 않습니다.");
//
//            deliveryAssignmentRepository.updateByUsername(updateReqDto.getUsernames().get(i));
//        }
//        return new CourierResUpdateDto("운송장 할당완료");
//    }
//}
//
