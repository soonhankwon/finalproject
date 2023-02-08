package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.SignupRequestDto;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    @Value("${manage.ADMIN_TOKEN}")
    private String ADMIN_TOKEN;

    public void registerAccount(SignupRequestDto requestDto) {
        validateAccountExistence(requestDto.getUsername());
        UserRoleEnum role = getUserRole(requestDto);
        accountRepository.save(createNewAccount(requestDto, role));
    }

    private void validateAccountExistence(String username) {
        if (accountRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("중복된 사용자 ID가 존재합니다.");
        }
    }

    private UserRoleEnum getUserRole(SignupRequestDto requestDto) {
        if (requestDto.isAdmin()) {
            validateAdminToken(requestDto.getAdminToken());
            return UserRoleEnum.ADMIN;
        }
        return UserRoleEnum.USER;
    }

    private void validateAdminToken(String adminToken) {
        if (!adminToken.equals(ADMIN_TOKEN)) {
            throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
        }
    }

    private Account createNewAccount(SignupRequestDto requestDto, UserRoleEnum role) {
        return new Account(requestDto.getUsername(),
                passwordEncoder.encode(requestDto.getPassword()),
                requestDto.getArea(), role);
    }
}
