package com.backendteam5.finalproject.controller;


import com.backendteam5.finalproject.dto.CourierResUpdateDto;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
 * 통합 테스트(모든 Bean들을 똑같이 IOC올리고 테스트 하는 것)
 * WebEnvironment.MOCK = 실제 톰켓을 올리는게 아니라, 다른 톰켓으로 테스트.
 * WebEnvironment.RANDOM_PORT = 실제 톰켓으로 테스트
 * @AutoConfigureMockMvc MockMvc를 IOC에 등록해줌.
 * @Transactional은 각각의 테스트 함수가 종료될 때마다 트랜잭션을 rollback해주는 어노테이션.
 */

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class CourierControllerIntegreTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private EntityManager em;

    private Courier courier;

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        courier = new Courier("구로구", 1, false, "수령인1", "배송완료날짜", "테스트 유저");
//        courierRepository.save(courier);

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

    @Nested
    @DisplayName("택배기사 배송 완료")
    class complete {

        // BDDMockito 패턴
        @Test
        @WithMockUser
        @DisplayName("택배기사 배송완료 정상 케이스")
        public void test() throws Exception {
            // given (테스트를 하기위한 준비)
            Long id = 2L;
            em.persist(courier);
            List<Courier> all = courierRepository.findAll();
            System.out.println("all.get(0).getId() = " + all.get(0).getId());

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

            // when(테스트 실행)
            ResultActions resultActions = mockMvc.perform(patch("/api/save/check")
                    .contentType(MediaType.ALL)
                    .with(csrf())
                    .accept(MediaType.ALL)
            );

            // then (검증 : 기댓값)
            resultActions
                    .andExpect(status().isBadRequest())
                    .andDo(MockMvcResultHandlers.print());
        }
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
            courier.setState(true);
            em.persist(courier);

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

    @Nested
    @DisplayName("배송 상태별 조회")
    class search {

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


