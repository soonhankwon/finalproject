package com.backendteam5.finalproject.service;


import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        String arrivalDate = formatter.format(date);
        String username = "";


        for (int i=0;i<=20;i++) {
            String index = Integer.toString(i);

            Courier courier = new Courier(area + index, state, customer + index, arrivalDate, username);
            courierRepository.save(courier);
        }






    }
}
