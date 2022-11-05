package com.backendteam5.finalproject.controller;


import com.backendteam5.finalproject.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @PostMapping("/test")
    public void dummieTest() {
        courierService.createDommie();
    }
}
