package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.AccountRepository;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final AccountRepository accountRepository;
    private final CourierRepository courierRepository;

    public List<Courier> searchCourier(UserDetailsImpl userDetails) {
        String route = userDetails.getUser().getRoute();
        return courierRepository.findByRoute(route);
    }

    public List<Account> searchUser(UserDetailsImpl userDetails) {
        String route = userDetails.getUser().getRoute();
        return accountRepository.findByRouteAndRole(route, UserRoleEnum.USER);
    }

    public List<Courier> searchRoute(List<Integer> subRoutes, UserDetailsImpl userDetails) {
        List<Courier> courierList = new LinkedList<>();
        for(int subRoute: subRoutes){
            courierList.addAll(courierRepository.findByRouteAndSubRoute(userDetails.getUser().getRoute(), subRoute));
        }
        return courierList;
    }

    public List<Courier> sortedCourier(String username, Boolean state, int arri) {
        List<Courier> courierList;
        if (arri == 1) {
            courierList= courierRepository.findByUsernameAndStateOrderByArrivalDateAsc(username, state);
        } else {
            courierList= courierRepository.findByUsernameAndStateOrderByArrivalDateDesc(username, state);
        }
        return courierList;
    }
}
