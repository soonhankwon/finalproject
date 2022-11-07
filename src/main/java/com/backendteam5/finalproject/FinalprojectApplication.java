package com.backendteam5.finalproject;

import com.backendteam5.finalproject.entity.CourierInfo;
import com.backendteam5.finalproject.repository.CourierInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class FinalprojectApplication implements CommandLineRunner {
    private final CourierInfoRepository courierInfoRepository;

    public static void main(String[] args) {
        SpringApplication.run(FinalprojectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception{
        List<CourierInfo> courierInfoList = new LinkedList<>();
        for(long i = 1L; i <= 20L; i++){
            courierInfoList.add(new CourierInfo("A", i, "구로구 "+ i +"지역"));
        }
        courierInfoRepository.saveAll(courierInfoList);
    }
}
