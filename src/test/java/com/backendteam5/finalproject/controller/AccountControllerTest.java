//package com.backendteam5.finalproject.controller;
//
//import com.backendteam5.finalproject.dto.SignupRequestDto;
//import com.backendteam5.finalproject.service.AccountService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@Slf4j
//@WebMvcTest(AccountController.class)
//class AccountControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AccountService accountService;
//
//    @Test
//    @WithMockUser
//    void login() throws Exception {
//
//        ResultActions resultActions = mockMvc.perform(get("/account/login")
//                .contentType(MediaType.ALL)
//                .with(csrf())
//                .accept(MediaType.ALL));
//
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(view().name("login"))
//                .andDo(MockMvcResultHandlers.print());
//    }
//
//    @Test
//    @WithMockUser
//    void signup() throws Exception {
//
//        ResultActions resultActions = mockMvc.perform(get("/account/signup")
//                .contentType(MediaType.ALL)
//                .with(csrf())
//                .accept(MediaType.ALL));
//
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(view().name("signup"))
//                .andDo(MockMvcResultHandlers.print());
//    }
//
//    @Test
//    @WithMockUser
//    void registerAccount() throws Exception {
//
//        SignupRequestDto requestDto = new SignupRequestDto();
//        requestDto.setUsername("택배기사");
//        requestDto.setPassword("비밀번호");
//        requestDto.setArea("관악구");
//
//        ResultActions resultActions = mockMvc.perform(post("/account/signup")
//                .contentType(MediaType.ALL)
//                .param("username", requestDto.getUsername())
//                .param("password", requestDto.getPassword())
//                .param("area", requestDto.getArea())
//                .with(csrf())
//                .accept(MediaType.ALL));
//
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(handler().handlerType(AccountController.class))
//                .andExpect(view().name("login"))
//                .andDo(MockMvcResultHandlers.print());
//    }
//}