package com.backendteam5.finalproject.service;


import com.backendteam5.finalproject.dto.SearchResponseDto;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.entity.CourierInfo;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;

    @Transactional
    public void createDommie() {

        String area = "A";
        Boolean state = false;
        String customer = "수령인";

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

        String arrivalDate = formatter.format(date);
        String username = "dlwotjs";


        for (int i=0;i<=20;i++) {
            String index = Integer.toString(i);

            Courier courier = new Courier(area + index, state, customer + index, arrivalDate, username);
            courierRepository.save(courier);
        }
    }

    public SearchResponseDto searchFilter(UserDetailsImpl userDetails, Long state) {


        Boolean status = false;
        if (state == 1) {
            status = true;
            List<Courier> courierList = courierRepository.findByUsernameAndStateOrderByArrivalDateDesc(userDetails.getUsername(), status);
            Long cnt = courierRepository.countByUsernameAndState(userDetails.getUsername(), status);
            return new SearchResponseDto(courierList, cnt);

        }
        List<Courier> courierList = courierRepository.findByUsernameAndStateOrderByArrivalDateDesc(userDetails.getUsername(), status);
        Long cnt = courierRepository.countByUsernameAndState(userDetails.getUsername(), status);
        return new SearchResponseDto(courierList, cnt);




    }

    public void searchCustomer(UserDetailsImpl userDetails, String customer) {



    }
}
