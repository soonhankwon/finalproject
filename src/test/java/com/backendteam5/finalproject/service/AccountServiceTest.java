package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.SignupRequestDto;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.CourierInfo;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.AccountRepository;
import com.backendteam5.finalproject.repository.CourierInfoRepository;
import com.backendteam5.finalproject.repository.CourierRepository;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@DisplayName("회원가입 단위테스트")
class AccountServiceTest {

    @Autowired
    AccountRepository accountRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    CourierInfoRepository courierInfoRepository;

    @Value("${manage.ADMIN_TOKEN}")
    private String ADMIN_TOKEN;

    private String username;
    private String password;
    private String route;
    private UserRoleEnum roleUser;
    private UserRoleEnum roleAdmin;

    private boolean admin;

    @BeforeEach
    void setUp() {
        // 기본 필드 생성.
        username = "test";
        password = "1234";
        route = "구로구";
        admin = false;
        roleUser = UserRoleEnum.USER;
        roleAdmin = UserRoleEnum.ADMIN;

        List<CourierInfo> courierInfoList = new LinkedList<>();
        for(int i = 1; i <= 20; i++){
            courierInfoList.add(new CourierInfo("A", i, "구로구 "+ i +"지역"));
        }
        courierInfoRepository.saveAll(courierInfoList);
    }


    @Test
    @DisplayName("유저 회원가입 정상 테스트")
    void successSignup() {
        // 회원가입Dto 생성
        SignupRequestDto signupRequestDto = getSignupRequestDto();

        // AccountService 생성자 생성후 Service의 registerAccount함수 실행
        AccountService accountService = getAccountService();
        accountService.registerAccount(signupRequestDto);
    }

    @Test
    @DisplayName("어드민 회원가입 정상 테스트")
    void adminSuccessSignup() {
        // 회원가입Dto 생성, Admin true로 변경(기본은 false), Admin토큰 할당.
        SignupRequestDto signupRequestDto = getSignupRequestDto();
        signupRequestDto.setAdmin(true);
        signupRequestDto.setAdminToken(ADMIN_TOKEN);

        // AccountService 생성자 생성후 Service의 registerAccount함수 실행
        AccountService accountService = getAccountService();
        accountService.registerAccount(signupRequestDto);
    }

    @Nested
    @DisplayName("실패 케이스")
    class failCases {

        @Nested
        @DisplayName("Account Username")
        class AccountUsername {

            @Test
            @DisplayName("아이디 중복확인")
            void failOverlapUsername() {
                // 정상 회원가입 한번 실행.
                successSignup();

                // 회원가입Dto 생성
                SignupRequestDto signupRequestDto = getSignupRequestDto();

                // AccountService 생성자 생성후 Service의 registerAccount함수 실행
                AccountService accountService = getAccountService();

                // 중복확인 에러 확인
                IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class, () -> {
                            accountService.registerAccount(signupRequestDto);
                        }
                );

                assertThat(exception.getMessage()).isEqualTo("중복된 사용자 ID가 존재합니다.");
            }
        }

        @Nested
        @DisplayName("어드민 토큰")
        class adminToken {

            @Test
            @DisplayName("어드민 토큰값 틀린경우")
            void notValidToken() {
                // 회원가입Dto 생성, Admin true로 변경(기본은 false), Admin토큰 틀리게 셋팅.
                SignupRequestDto signupRequestDto = getSignupRequestDto();
                signupRequestDto.setAdmin(true);
                signupRequestDto.setAdminToken("아무토큰값");

                // service생성자 생성.
                AccountService accountService = getAccountService();

                // 어드민 토큰값 틀린경우 에러 확인
                IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class, () -> {
                            accountService.registerAccount(signupRequestDto);
                        }
                );
                assertThat(exception.getMessage()).isEqualTo("관리자 암호가 틀려 등록이 불가능합니다.");
            }

            @Test
            @DisplayName("어드민 토큰값 없는경우")
            void emptyToken() {
                // 회원가입Dto 생성, Admin true로 변경(기본은 false), Admin토큰은 빈값.
                SignupRequestDto signupRequestDto = getSignupRequestDto();
                signupRequestDto.setAdmin(true);

                // Service생성자 생성.
                AccountService accountService = getAccountService();

                // 어드민 토큰 입력안한경우 에러 확인.
                IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class, () -> {
                            accountService.registerAccount(signupRequestDto);
                        }
                );
                assertThat(exception.getMessage()).isEqualTo("관리자 암호가 틀려 등록이 불가능합니다.");
            }
        }
    }

    private AccountService getAccountService() {
        return new AccountService(
                passwordEncoder,
                accountRepository,
                courierInfoRepository
        );
    }

    private SignupRequestDto getSignupRequestDto() {
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(username);
        signupRequestDto.setArea(route);
        signupRequestDto.setPassword(password);
        signupRequestDto.setAdmin(admin);
        return signupRequestDto;
    }

}