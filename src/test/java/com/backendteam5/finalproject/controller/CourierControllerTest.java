package com.backendteam5.finalproject.controller;

import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import com.backendteam5.finalproject.service.CourierService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 단위테스트 (Controller)

@Slf4j
@WebMvcTest(CourierController.class)
public class CourierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourierService courierService;


    @Nested
    @DisplayName("택배기사 배송 완료")
    class complete {

        // BDDMockito 패턴
        @Test
        @WithMockUser
        @DisplayName("택배기사 배송완료 정상 케이스")
        public void test() throws Exception {

            // given (테스트를 하기위한 준비)
            Long id = 1L;
            CourierResUpdateDto updateDto = new CourierResUpdateDto("배송완료");
            when(courierService.checkCourierState(id)).thenReturn(updateDto);

            // when(테스트 실행)
            ResultActions resultActions = mockMvc.perform(patch("/api/save/check")
                    .contentType(MediaType.ALL)
                    .param("courierId", String.valueOf(id))
                    .with(csrf())
                    .accept(MediaType.ALL)
            );


            // then (검증 : 기댓값)
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.msg").value("배송완료"))
                    .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @WithMockUser
        @DisplayName("택배기사 배송완료 실패")
        public void failCase() throws Exception {
            // given (테스트를 하기위한 준비)
            Long id = 1L;
            CourierResUpdateDto updateDto = new CourierResUpdateDto("배송완료");
            when(courierService.checkCourierState(id)).thenReturn(updateDto);


            // when(테스트 실행)
            ResultActions resultActions = mockMvc.perform(patch("/api/save/check")
                    .contentType(MediaType.ALL)
                    .with(csrf())
                    .accept(MediaType.ALL)
            );


            // then (검증 : 기댓값)
            resultActions
                    .andExpect(status().isBadRequest())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.msg").value("배송완료"))
                    .andDo(MockMvcResultHandlers.print());

        }


        @Nested
        @DisplayName("택배기사 배송완료 취소")
        class cancel {

            @Test
            @DisplayName("정상 케이스")
            @WithMockUser
            public void successCase() throws Exception {
                // given (테스트를 하기위한 준비)
                Long id = 1L;
                CourierResUpdateDto updateDto = new CourierResUpdateDto("배송대기상태로 수정되었습니다.");
                when(courierService.uncheckCourierState(id)).thenReturn(updateDto);

                // when(테스트 실행)
                ResultActions resultActions = mockMvc.perform(patch("/api/save/uncheck")
                        .contentType(MediaType.ALL)
                        .param("courierId", String.valueOf(id))
                        .with(csrf())
                        .accept(MediaType.ALL)
                );

                // then (검증 : 기댓값)
                resultActions
                .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.msg").value("배송대기상태로 수정되었습니다."))
                        .andDo(MockMvcResultHandlers.print());
            }

            @Test
            @WithMockUser
            @DisplayName("실패 케이스")
            public void failCase() throws Exception {

                // given (테스트를 하기위한 준비)
                Long id = 1L;
                CourierResUpdateDto updateDto = new CourierResUpdateDto("배송대기상태로 수정되었습니다.");
                when(courierService.uncheckCourierState(id)).thenReturn(updateDto);

                // when(테스트 실행)
                ResultActions resultActions = mockMvc.perform(patch("/api/save/uncheck")
                        .contentType(MediaType.ALL)
                        .with(csrf())
                        .accept(MediaType.ALL)
                );

                resultActions
                        .andExpect(status().isBadRequest())
                        .andDo(MockMvcResultHandlers.print());
            }
        }
    }

    @Nested
    @DisplayName("배송 상태별 조회")
    class search {

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

            userDetails = new UserDetailsImpl(account);
        }

        @Test
        @WithMockUser
        @DisplayName("정상 케이스")
        public void successCase() throws Exception {


            // given (테스트를 하기위한 준비)
            ResultActions resultActions = mockMvc.perform(get("/api/search/user/courier").with(user(userDetails))
                    .queryParam("state", String.valueOf(0L))
            );

            resultActions
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("수령인 이름으로 조회")
    class customerSearch {

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

            userDetails = new UserDetailsImpl(account);
        }

        @Test
        @WithMockUser
        @DisplayName("정상 케이스")
        public void successCase() throws Exception {



            // given (테스트를 하기위한 준비)
            ResultActions resultActions = mockMvc.perform(get("/api/search/user/courier/customer").with(user(userDetails))
                    .queryParam("customer", "jasun")
            );

            resultActions
                    .andExpect(status().isOk());

        }
    }
}