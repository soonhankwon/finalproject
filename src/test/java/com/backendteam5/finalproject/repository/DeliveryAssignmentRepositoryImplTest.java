//package com.backendteam5.finalproject.repository;
//
//import com.backendteam5.finalproject.dto.DeliveryAssignmentDto;
//import com.backendteam5.finalproject.dto.UserDto;
//import com.backendteam5.finalproject.entity.UserRoleEnum;
//import com.backendteam5.finalproject.repository.custom.CustomAccountRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class DeliveryAssignmentRepositoryImplTest {
//    @Autowired
//    CustomAccountRepository accountRepository;
//
//    @Autowired
//    DeliveryAssignmentRepositoryImpl deliveryAssignmentRepository;
//
//    @Test
//    void selectDelivery() {
//        List<UserDto> userDtos = accountRepository.findByAreaAndRole("GURO", UserRoleEnum.USER);
//        userDtos.forEach(System.out::println);
//
//        List<DeliveryAssignmentDto> result = deliveryAssignmentRepository.selectDelivery("GURO", "A", "GUROADMIN");
//        result.forEach(System.out::println);
//    }
//}