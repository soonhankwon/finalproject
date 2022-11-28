package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.CourierDto;
import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.dto.SearchResponseDto;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

/*
 * 단위 테스트 (Service와 관련된 애들만 메모리에 띄우면 됨.)
 * CourierRepository => 가짜 객체로 만들 수 있음.
 * @Mock -> Mockito라는 메모리 공간에 띄어짐
 */


@ExtendWith(MockitoExtension.class)
public class CourierServiceTest {

    @InjectMocks // CourierService 객체가 만들어질 때 CourierServiceTest 파일에 @Mock로 등록된 모든 애들을 주입받는다.
    private CourierService courierService;

    @Mock
    private CourierRepository courierRepository;


    @BeforeEach
    void init() {
        Courier courier = new Courier(
                "구로구",
                1,
                false,
                "수령인",
                "받는 날짜",
                "배송기사"
        );

        courierRepository.save(courier);
    }

    @Nested
    @DisplayName("택배 운송완료 테스트")
    class checkCourierState {


        @Test
        @DisplayName("택배 운송완료 정상")
        public void successCase() {

            // given
            Courier courier = new Courier(
                    "구로구",
                    1,
                    false,
                    "수령인",
                    "받는 날짜",
                    "배송기사"
            );
            given(courierRepository.findById(1L)).willReturn(Optional.of(courier));

            // when
            CourierResUpdateDto updateDto = courierService.checkCourierState(1L);
            System.out.println("updateDto.getMsg() = " + updateDto.getMsg());

            // then
            assertThat(updateDto).isNotNull();
            assertThat(updateDto.getMsg()).isEqualTo("배송완료");
        }

        @Test
        @DisplayName("실패케이스 운송장 없는경우")
        public void notFoundCase() {
            // given
            given(courierRepository.findById(2L))
                    .willThrow(new NullPointerException("해당 운송장이 존재하지 않습니다"));

            // when
            NullPointerException exception = assertThrows(
                    NullPointerException.class, () -> {
                        courierService.checkCourierState(2L);
                    }
            );

            // then
            assertThat(exception.getMessage()).isEqualTo("해당 운송장이 존재하지 않습니다");
            assertThat(exception.getClass()).isEqualTo(NullPointerException.class);
        }

        @Test
        @DisplayName("실패케이스 이미 배송완료했을 경우")
        public void overlapCase() {

            // given
            Courier courier = new Courier(
                    "구로구",
                    1,
                    true,
                    "수령인",
                    "받는 날짜",
                    "배송기사"
            );
            given(courierRepository.findById(1L)).willReturn(Optional.of(courier));

            // when
            CourierResUpdateDto updateDto = courierService.checkCourierState(1L);

            // then
            assertThat(updateDto).isNotNull();
            assertThat(updateDto.getClass()).isEqualTo(CourierResUpdateDto.class);
            assertThat(updateDto.getMsg()).isEqualTo("해당 운송장상태 변경이 불가능합니다.");
        }
    }


    @Nested
    @DisplayName("택배 배송완료 취소")
    class uncheckCourierState {

        @Test
        @DisplayName("배송완료 취소 테스트")
        public void successCase() {

            // given
            Courier courier = new Courier(
                    "구로구",
                    1,
                    true,
                    "수령인",
                    "받는 날짜",
                    "배송기사"
            );
            given(courierRepository.findById(1L)).willReturn(Optional.of(courier));

            // when
            CourierResUpdateDto updateDto = courierService.uncheckCourierState(1L);

            // then
            assertThat(updateDto).isNotNull();
            assertThat(updateDto.getMsg()).isEqualTo("배송대기상태로 수정되었습니다.");
            assertThat(updateDto.getClass()).isEqualTo(CourierResUpdateDto.class);
        }

        @Test
        @DisplayName("배송완료 취소 중복 테스트")
        public void overlap() {

            // given
            Courier courier = new Courier(
                    "구로구",
                    1,
                    false,
                    "수령인",
                    "받는 날짜",
                    "배송기사"
            );
            given(courierRepository.findById(1L)).willReturn(Optional.of(courier));


            // when
            CourierResUpdateDto updateDto = courierService.uncheckCourierState(1L);

            // then
            assertThat(updateDto).isNotNull();
            assertThat(updateDto.getMsg()).isEqualTo("해당 운송장은 배송대기중입니다.");
            assertThat(updateDto.getClass()).isEqualTo(CourierResUpdateDto.class);
        }


        @Test
        @DisplayName("배송완료 취소 운송장 없는 경우")
        public void notFound() {
            // given
            given(courierRepository.findById(1L))
                    .willThrow(new NullPointerException("해당 운송장이 존재하지 않습니다"));

            // when
            NullPointerException exception = assertThrows(
                    NullPointerException.class, () -> {
                        courierService.checkCourierState(1L);
                    }
            );

            // then
            assertThat(exception.getMessage()).isEqualTo("해당 운송장이 존재하지 않습니다");
            assertThat(exception.getClass()).isEqualTo(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("택배기사 페이지 택배 배송 상태별 조회.")
    class searchFilter {

        private UserDetailsImpl userDetails;

        @BeforeEach
        void mockUserSetUp() {
            // mock 테스트 유저 생성
            String username = "테스트 유저";
            String password = "패스워드";
            String route = "구로구";
            UserRoleEnum roleEnum = UserRoleEnum.USER;

            Account account = new Account(
                    username,
                    password,
                    route,
                    roleEnum
            );

            userDetails =  new UserDetailsImpl(account);
        }

        @Test
        @DisplayName("정상 케이스")
        public void successCase() {

            CourierDto courier = new CourierDto(
                    1L,
                    "구로구2",
                    1,
                    false,
                    "수령인2",
                    "받는 날짜2",
                    "테스트 유저"
            );

            CourierDto courier2 = new CourierDto(
                    2L,
                    "구로구2",
                    1,
                    false,
                    "수령인2",
                    "받는 날짜2",
                    "테스트 유저"
            );


            List<CourierDto> courierList = new ArrayList<>();
            courierList.add(courier);
            courierList.add(courier2);

            // when
            when(courierRepository.searchByUsernameAndState("테스트 유저", false))
                    .thenReturn(courierList);
            when(courierRepository.countUsernameAndState("테스트 유저", false))
                    .thenReturn(2L);

            when(courierRepository.countUsernameAndState("테스트 유저", true))
                    .thenReturn(0L);



            SearchResponseDto responseDto = courierService.searchFilter(userDetails, 0L);

            System.out.println("responseDto.getData() = " + responseDto.getData());
            System.out.println("responseDto.getCompleteCnt() = " + responseDto.getCompleteCnt());
            System.out.println("responseDto.getProgressCnt() = " + responseDto.getProgressCnt());

            assertThat(responseDto.getProgressCnt()).isEqualTo(2L);
            assertThat(responseDto.getCompleteCnt()).isEqualTo(0L);
            assertThat(responseDto.getData().size()).isEqualTo(2);
            assertThat(responseDto.getData().get(0).getUsername()).isEqualTo("테스트 유저");
            assertThat(responseDto.getData().get(0).getRoute()).isEqualTo("구로구2");
        }
    }

    @Nested
    @DisplayName("수령인 이름으로 조회")
    class searchCustomer {

        private UserDetailsImpl userDetails;

        @BeforeEach
        void mockUserSetUp() {
            // mock 테스트 유저 생성
            String username = "테스트 유저";
            String password = "패스워드";
            String route = "구로구";
            UserRoleEnum roleEnum = UserRoleEnum.USER;

            Account account = new Account(
                    username,
                    password,
                    route,
                    roleEnum
            );

            userDetails =  new UserDetailsImpl(account);
        }

        @Test
        @DisplayName("수령인 이름으로 조회 정상테스트")
        public void searchCustomer() {


            CourierDto courier2 = new CourierDto(
                    2L,
                    "구로구2",
                    1,
                    false,
                    "수령인2",
                    "받는 날짜2",
                    "테스트 유저"
            );

            List<CourierDto> courierList = new ArrayList<>();
            courierList.add(courier2);

            // when
            when(courierRepository.searchCustomer("수령인2"))
                    .thenReturn(courierList);
            when(courierRepository.countUsernameAndState("테스트 유저", false))
                    .thenReturn(1L);

            when(courierRepository.countUsernameAndState("테스트 유저", true))
                    .thenReturn(0L);

            SearchResponseDto responseDto = courierService.searchCustomer(userDetails, "수령인2");

            System.out.println("responseDto.getData() = " + responseDto.getData());

            assertThat(responseDto.getData().size()).isEqualTo(1);
            assertThat(responseDto.getData().get(0).getCustomer()).isEqualTo("수령인2");
            assertThat(responseDto.getProgressCnt()).isEqualTo(1L);
            assertThat(responseDto.getCompleteCnt()).isEqualTo(0L);
        }
    }

}