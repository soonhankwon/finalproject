package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.AdminMainResDto;
import com.backendteam5.finalproject.dto.CourierReqUpdateDto;
import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.AccountRepository;
import com.backendteam5.finalproject.repository.CourierRepository;
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

    public AdminMainResDto findAll(UserDetailsImpl userDetails) {
        String route = userDetails.getUser().getRoute();
        return new AdminMainResDto(accountRepository.findByRouteAndRole(route, UserRoleEnum.USER),
                courierRepository.findByRoute(route));
    }

    public AdminMainResDto searchCourier(Long courierId, UserDetailsImpl userDetails) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(
                () -> new IllegalArgumentException("해당 courier가 존재하지 않습니다.")
        );
        Account account = accountRepository.findByUsername(courier.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("해당 배정자가 존재하지 않습니다.")
        );
        return new AdminMainResDto(Collections.singletonList(account),
                Collections.singletonList(courier));
    }

    public AdminMainResDto sortedCourier(String username, List<Integer> subRoute,
                                         int state, Boolean arri, UserDetailsImpl userDetails) {
        List<Courier> courierList = new LinkedList<>();
        Account account = userDetails.getUser();

        if (username.isEmpty()) {
            if (subRoute.isEmpty()) {
                if (state == 2) {
                    courierList = arri ? courierRepository.findByRouteOrderByArrivalDateAsc(account.getRoute()) :
                            courierRepository.findByRouteOrderByArrivalDateDesc(account.getRoute());
                } else if (state == 1) {
                    courierList = arri ? courierRepository.findByRouteAndStateOrderByArrivalDateAsc(account.getRoute(), true) :
                            courierRepository.findByRouteAndStateOrderByArrivalDateDesc(account.getRoute(), true);
                } else {
                    courierList = arri ? courierRepository.findByRouteAndStateOrderByArrivalDateAsc(account.getRoute(), false) :
                            courierRepository.findByRouteAndStateOrderByArrivalDateDesc(account.getRoute(), false);
                }
            } else {
                if (state == 2) {
                    for (int subroute : subRoute) {
                        courierList.addAll(arri ? courierRepository.findByRouteAndSubRouteOrderByArrivalDateAsc(account.getRoute(), subroute) :
                                courierRepository.findByRouteAndSubRouteOrderByArrivalDateDesc(account.getRoute(), subroute));
                    }
                } else if (state == 1) {
                    for (int subroute : subRoute) {
                        courierList.addAll(arri ? courierRepository.findByRouteAndSubRouteAndStateOrderByArrivalDateAsc(account.getRoute(), subroute, true) :
                                courierRepository.findByRouteAndSubRouteAndStateOrderByArrivalDateDesc(account.getRoute(), subroute, true));
                    }
                } else {
                    for (int subroute : subRoute) {
                        courierList.addAll(arri ? courierRepository.findByRouteAndSubRouteAndStateOrderByArrivalDateAsc(account.getRoute(), subroute, false) :
                                courierRepository.findByRouteAndSubRouteAndStateOrderByArrivalDateDesc(account.getRoute(), subroute, false));
                    }
                }
            }
        } else {
            if (subRoute.isEmpty()) {
                if (state == 2) {
                    courierList = arri ? courierRepository.findByRouteAndUsernameOrderByArrivalDateAsc(account.getRoute(), username) :
                            courierRepository.findByRouteAndUsernameOrderByArrivalDateDesc(account.getRoute(), username);
                } else if (state == 1) {
                    courierList = arri ? courierRepository.findByRouteAndUsernameAndStateOrderByArrivalDateAsc(account.getRoute(), username, true) :
                            courierRepository.findByRouteAndUsernameAndStateOrderByArrivalDateDesc(account.getRoute(), username, true);
                } else {
                    courierList = arri ? courierRepository.findByRouteAndUsernameAndStateOrderByArrivalDateAsc(account.getRoute(), username, false) :
                            courierRepository.findByRouteAndUsernameAndStateOrderByArrivalDateDesc(account.getRoute(), username, false);
                }
            } else {
                if (state == 2) {
                    for (int subroute : subRoute) {
                        courierList.addAll(arri ? courierRepository.findByRouteAndUsernameAndSubRouteOrderByArrivalDateAsc(account.getRoute(), username, subroute) :
                                courierRepository.findByRouteAndUsernameAndSubRouteOrderByArrivalDateDesc(account.getRoute(), username, subroute));
                    }
                } else if (state == 1) {
                    for (int subroute : subRoute) {
                        courierList.addAll(arri ? courierRepository.findByRouteAndUsernameAndSubRouteAndStateOrderByArrivalDateAsc(account.getRoute(), username, subroute, true) :
                                courierRepository.findByRouteAndUsernameAndSubRouteAndStateOrderByArrivalDateDesc(account.getRoute(), username, subroute, true));
                    }
                } else {
                    for (int subroute : subRoute) {
                        courierList.addAll(arri ? courierRepository.findByRouteAndUsernameAndSubRouteAndStateOrderByArrivalDateAsc(account.getRoute(), username, subroute, false) :
                                courierRepository.findByRouteAndUsernameAndSubRouteAndStateOrderByArrivalDateDesc(account.getRoute(), username, subroute, false));
                    }
                }
            }
        }
        return new AdminMainResDto(checkuser(account, username), courierList);
    }

    public List<Account> checkuser(Account account, String username) {
        List<Account> accountList;
        if (username.isEmpty() || username.equals(account.getUsername())) {
            accountList = accountRepository.findByRouteAndRole(account.getRoute(), UserRoleEnum.USER);
        } else {
            Account user = accountRepository.findByUsername(username).orElseThrow(
                    () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
            );
            accountList = Collections.singletonList(user);
        }
        return accountList;
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
}
