package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.SignupRequestDto;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AccountService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    @Value("${manage.ADMIN_TOKEN}")
    private String ADMIN_TOKEN;

    @Autowired
    public AccountService(BCryptPasswordEncoder passwordEncoder, AccountRepository accountRepository){
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }
    public void registerAccount(SignupRequestDto requestDto) {
        Optional<Account> account = accountRepository.findByUsername(requestDto.getUsername());
        if(account.isPresent()) throw new IllegalArgumentException("중복된 사용자 ID가 존재합니다.");
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        accountRepository.save(new Account(requestDto.getUsername(), passwordEncoder.encode(requestDto.getPassword()), requestDto.getArea(),role));
    }
}
