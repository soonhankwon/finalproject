//package com.backendteam5.finalproject.repository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;
//
//// 단위테스트 (DB 관련된 Bean이 IOC에 등록되면 됨)
//
//
//@Transactional
//@AutoConfigureTestDatabase(replace = Replace.ANY) // Replace.ANY 가짜 디비로 테스트 , Replace.NONE 실제 DB로 테스트
//@DataJpaTest // Repository들을 IOC에 등록해줌.
//public class CourierRepositoryTest {
//
//    @Autowired
//    private CourierRepository courierRepository;
//
//}