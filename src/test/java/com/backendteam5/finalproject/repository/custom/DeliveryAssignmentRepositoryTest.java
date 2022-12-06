package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.CountDirect;
import com.backendteam5.finalproject.dto.DeliveryAssignmentDto;
import com.backendteam5.finalproject.dto.MainDto;
import com.backendteam5.finalproject.entity.*;
import com.backendteam5.finalproject.repository.AccountRepository;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.repository.DeliveryAssignmentRepository;
import com.querydsl.core.Tuple;
import org.hibernate.hql.spi.id.TableBasedUpdateHandlerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class DeliveryAssignmentRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    DeliveryAssignmentRepository deliveryAssignmentRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CourierRepository courierRepository;

//    @BeforeEach
//    public void before() {
//        String[] area = {"구로구", "송파구", "신촌구", "부천구", "중동구"};
//        String[] route = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
//
//        Account ADMIN1 = new Account("GUROADMIN", "1234",
//                "구로구", UserRoleEnum.ADMIN);
//        em.persist(ADMIN1);
//
//        Account ADMIN2 = new Account("SONGPAADMIN", "1234",
//                "송파구", UserRoleEnum.ADMIN);
//        em.persist(ADMIN2);
//
//        Account ADMIN3 = new Account("SINCHONADMIN", "1234",
//                "신촌구", UserRoleEnum.ADMIN);
//        em.persist(ADMIN3);
//
//        Account ADMIN4 = new Account("BUCHONADMIN", "1234",
//                "부천구", UserRoleEnum.ADMIN);
//        em.persist(ADMIN4);
//
//        Account ADMIN5 = new Account("JOUNDONGADMIN", "1234",
//                "중동구", UserRoleEnum.ADMIN);
//        em.persist(ADMIN5);
//
//
//        Account USER1_1 = new Account("GUROUSER1", "1234",
//                "구로구", UserRoleEnum.USER);
//        em.persist(USER1_1);
//        Account USER1_2 = new Account("GUROUSER2", "1234",
//                "구로구", UserRoleEnum.USER);
//        em.persist(USER1_2);
//
//        Account USER2_1 = new Account("SONGPAUSER1", "1234",
//                "송파구", UserRoleEnum.USER);
//        em.persist(USER2_1);
//
//        Account USER2_2 = new Account("SONGPAUSER2", "1234",
//                "송파구", UserRoleEnum.USER);
//        em.persist(USER2_2);
//
//        Account USER3_1 = new Account("SINCHONUSER1", "1234",
//                "신촌구", UserRoleEnum.USER);
//        em.persist(USER3_1);
//        Account USER3_2 = new Account("SINCHONUSER2", "1234",
//                "신촌구", UserRoleEnum.USER);
//        em.persist(USER3_2);
//
//        Account USER4_1 = new Account("BUCHONUSER1", "1234",
//                "부천구", UserRoleEnum.USER);
//        em.persist(USER4_1);
//        Account USER4_2 = new Account("BUCHONUSER2", "1234",
//                "부천구", UserRoleEnum.USER);
//        em.persist(USER4_2);
//
//        Account USER5_1 = new Account("JOUNDONGUSER1", "1234",
//                "중동구", UserRoleEnum.USER);
//        em.persist(USER5_1);
//        Account USER5_2 = new Account("JOUNDONGUSER2", "1234",
//                "중동구", UserRoleEnum.USER);
//        em.persist(USER5_2);
//
//        Account[][] accounts = {{USER1_1, USER1_2}, {USER2_1, USER2_2}, {USER3_1, USER3_2},
//                {USER4_1, USER4_2}, {USER5_1, USER5_2}};
//
//        for (int i = 0; i < area.length; i++) {
//            for (int j = 0; j < route.length; j++) {
//                for (int z = 0; z < 10; z++) {
//                    AreaIndex tempArea = new AreaIndex(area[i], route[j],
//                            z+1, String.format("%1$03d", i * 100 + j * 10 + z + 1));
//                    em.persist(tempArea);
//
//                    DeliveryAssignment deliveryAssignment = new DeliveryAssignment(accounts[i][z % 2], tempArea);
//                    em.persist(deliveryAssignment);
////                    em.persist(new DeliveryAssignment(USER1_1, tempArea));
//                }
//            }
//        }
//    }

    @DisplayName("Delivery를 수정을 위해서 지역별로 조회하는 메소드에 대한 테스트")
    @Test
    public void selectDeliveryTest() {
        List<DeliveryAssignmentDto> result = deliveryAssignmentRepository.selectDelivery("구로구", "C");
        for(DeliveryAssignmentDto one : result){
            System.out.println(one);
        }
    }

    @DisplayName("Delivery를 업데이트 하는 메소드 테스트 성공")
    @Test
    public void updateDeliveryTestOK(){
        String zipCode = "001";
        String username = "JOUNDONGUSER2";

        assertThat(deliveryAssignmentRepository.updateDelivery(zipCode, username)).isEqualTo(1);
    }

    @DisplayName("Delivery를 업데이트 하는 메소드 테스트 실패 - 없는 username")
    @Test
    public void updateDeliveryTestFail(){
        String zipCode = "001";
        String username = "JOUNDONGUSER3";

        assertThat(deliveryAssignmentRepository.updateDelivery(zipCode, username)).isEqualTo(1);
    }

    @DisplayName("임시 할당된 갯수에 대한 테스트")
    @Test
    public void countTemp(){
        Account user = accountRepository.findByAreaAndRole("구로구", UserRoleEnum.USER).get(0);
        System.out.println(courierRepository.countUsernameTemp(user, "2022-12-03"));
    }
    @DisplayName("직접 할당된 갯수에 대한 테스트")
    @Test
    public void countDirect(){
        Account user = accountRepository.findByAreaAndRole("구로구", UserRoleEnum.USER).get(0);
        System.out.println(courierRepository.countUsernameDirect(user, "2022-12-03"));
    }

    @DisplayName("왼쪽 테이블을 위한 보내는 데이터")
    @Test
    public void mainDtoSend(){
        MainDto mainDto = new MainDto();
        List<Account> userlist = accountRepository.findByAreaAndRole("구로구", UserRoleEnum.USER);
        String date = "2022-12-03";
        mainDto.setUserlist(userlist);

        LinkedList<Long> tempCount = new LinkedList<>();
        LinkedList<CountDirect> directCount = new LinkedList<>();

        for(Account user : userlist){
            Long aLong = courierRepository.countUsernameTemp(user, date);
            List<CountDirect> countDirects = courierRepository.countUsernameDirect(user, date);
            tempCount.add(aLong);
            if(countDirects.isEmpty())  {
                directCount.add(new CountDirect("배송중", 0L));
                directCount.add(new CountDirect("배송완료", 0L));
                directCount.add(new CountDirect("배송지연", 0L));
            } else  directCount.addAll(countDirects);
        }
        mainDto.setTempAssignment(tempCount);
        mainDto.setDirectAssignment(directCount);
        System.out.println(mainDto);
    }
    private String convertNowDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }
}