package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.MainResponseDto;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.AccountRepository;
import com.backendteam5.finalproject.repository.CourierInfoRepository;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final AccountRepository accountRepository;
    private final CourierRepository courierRepository;
    private final CourierInfoRepository courierInfoRepository;

    public MainResponseDto searchAll(UserDetailsImpl userDetails) {
        String route = userDetails.getUser().getRoute();
        return new MainResponseDto(accountRepository.findByRouteAndRole(route, UserRoleEnum.USER),
                courierRepository.findByRouteStartingWith(route));
    }
}