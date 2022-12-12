//package com.backendteam5.finalproject.service;
//
//import com.backendteam5.finalproject.dto.SignupRequestDto;
//import com.backendteam5.finalproject.entity.UserRoleEnum;
//import com.backendteam5.finalproject.repository.AccountRepository;
//import com.backendteam5.finalproject.repository.AreaIndexRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//
//
//@ExtendWith(MockitoExtension.class)
//class AccountServiceTest {
//
//    @InjectMocks // CourierService 객체가 만들어질 때 CourierServiceTest 파일에 @Mock로 등록된 모든 애들을 주입받는다.
//    private AccountService accountService;
//
//    @Mock
//    private AccountRepository accountRepository;
//
//    @Mock
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Mock
//    private AreaIndexRepository courierInfoRepository;
//
//    @Nested
//    @DisplayName("회원가입")
//    class signup {
//
//        private String ADMIN_TOKEN;
//        private SignupRequestDto requestDto;
//        private UserRoleEnum role;
//
//        @Test
//        void setUp() {
//            role = UserRoleEnum.USER;
//            ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";
//
//            requestDto = new SignupRequestDto();
//            requestDto.setUsername("택배기사");
//            requestDto.setArea("구로구");
//            requestDto.setPassword("비밀번호");
//
//        }
//
//        @Test
//        @DisplayName("정상 테스트")
//        void registerAccount() {
//            accountService.registerAccount(requestDto);
//        }
//
//        @Test
//        @DisplayName("중복된 사용자 실패케이스")
//        void overlapUsername() {
//
//            given(accountRepository.findByUsername("택배기사")).willThrow(new IllegalArgumentException("중복된 사용자 ID가 존재합니다."));
//
//            SignupRequestDto requestDto2 = new SignupRequestDto();
//            requestDto2.setUsername("택배기사");
//            requestDto2.setArea("구로구");
//            requestDto2.setPassword("비밀번호");
//
//            IllegalArgumentException exception = assertThrows(
//                    IllegalArgumentException.class, () -> {
//                        accountService.registerAccount(requestDto2);
//                    }
//            );
//            System.out.println("exception.getMessage() = " + exception.getMessage());
//
//            assertThat(exception.getMessage()).isEqualTo("중복된 사용자 ID가 존재합니다.");
//            assertThat(exception.getClass()).isEqualTo(IllegalArgumentException.class);
//        }
//
//        @Test
//        @DisplayName("관리자 암호 틀린경우")
//        void notValidAdminToken() {
//            SignupRequestDto requestDto2 = new SignupRequestDto();
//            requestDto2.setUsername("택배기사");
//            requestDto2.setArea("구로구");
//            requestDto2.setPassword("비밀번호");
//            requestDto2.setAdminToken(ADMIN_TOKEN = "123dsaf");
//            requestDto2.setAdmin(true);
//
//            IllegalArgumentException exception = assertThrows(
//                    IllegalArgumentException.class, () -> {
//                        accountService.registerAccount(requestDto2);
//                    }
//            );
//
//            assertThat(exception.getMessage()).isEqualTo("관리자 암호가 틀려 등록이 불가능합니다.");
//            assertThat(exception.getClass()).isEqualTo(IllegalArgumentException.class);
//        }
//    }
//
//    @Test
//    @DisplayName("구를 입력하지 않았을때")
//    void notFoundArea() {
//        SignupRequestDto requestDto2 = new SignupRequestDto();
//        requestDto2.setUsername("택배기사");
//        requestDto2.setPassword("비밀번호");
//
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class, () -> {
//                    accountService.registerAccount(requestDto2);
//                }
//        );
//
//        assertThat(exception.getMessage()).isEqualTo("입력하신 구가 없습니다.");
//        assertThat(exception.getClass()).isEqualTo(IllegalArgumentException.class);
//    }
//
//}