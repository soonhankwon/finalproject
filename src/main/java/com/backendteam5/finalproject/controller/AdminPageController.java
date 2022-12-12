package com.backendteam5.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    // admin detail하게 수정하는 창
    @GetMapping("/detailSave")
    public String detailSave(){return "detailSave";}

    // Delivery 수정창
    @GetMapping("/delivery")
    public String deliveryPage() {return "setDelivery";}
}
