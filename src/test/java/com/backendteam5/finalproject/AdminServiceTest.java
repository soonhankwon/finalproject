package com.backendteam5.finalproject;

import com.backendteam5.finalproject.dto.UpdateReqDto;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.AccountRepository;
import com.backendteam5.finalproject.repository.CourierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("관리자용 업데이트기능 단위테스트")
class AdminServiceTest {

    @Autowired
    AccountRepository accountRepository;
    CourierRepository courierRepository;
    EntityManager em;

    @BeforeEach
    public void before() {
        Courier courier1 = new Courier("A", 1, false, "soonhan", "11-25", "ADMIN");
        Courier courier2 = new Courier("A", 1, false, "soonhan", "11-25", "ADMIN");
        Courier courier3 = new Courier("A", 1, false, "soonhan", "11-25", "ADMIN");

        Account account1 = new Account("ADMIN", "1", "A", UserRoleEnum.ADMIN);
        Account account2 = new Account("user1", "1", "A", UserRoleEnum.USER);
        Account account3 = new Account("user2", "1", "A", UserRoleEnum.USER);

        em.persist(courier1);
        em.persist(courier2);
        em.persist(courier3);
        em.persist(account1);
        em.persist(account2);
        em.persist(account3);
    }

    @Test
    @DisplayName("대량 운송장 업데이트 기능 테스트")
    public void updateCouriers() throws Exception {
        //given
        UpdateReqDto updateReqDto = getUpdateReqDto();

        //when
        if (updateReqDto.getUsernames().size() == 1) {
            Account account = accountRepository.findByUsername(updateReqDto.getUsernames().get(0))
                    .orElseThrow(() -> new NullPointerException("해당 계정이 존재하지 않습니다."));
            for (int i = 0; i < updateReqDto.getCourierIds().size(); i++) {
                courierRepository.updateByCourierId(updateReqDto.getCourierIds().get(i), account.getUsername());
            }
        }
        //then
        Optional<Courier> courier4 = courierRepository.findById(1L);
        Optional<Courier> courier5 = courierRepository.findById(2L);
        Optional<Courier> courier6 = courierRepository.findById(3L);
        assertThat(courier4.get().getUsername()).isEqualTo("soonhan");
        assertThat(courier5.get().getUsername()).isEqualTo("soonhan");
        assertThat(courier6.get().getUsername()).isEqualTo("soonhan");
    }

    private UpdateReqDto getUpdateReqDto() {
        UpdateReqDto updateReqDto = new UpdateReqDto();
        List<String> usernames = new ArrayList<>();
        List<Long> courierIds = new ArrayList<>();
        usernames.add("soonhan");
        courierIds.add(1L);
        courierIds.add(2L);
        courierIds.add(3L);

        updateReqDto.setUsernames(usernames);
        updateReqDto.setCourierIds(courierIds);
        return updateReqDto;
    }
}
