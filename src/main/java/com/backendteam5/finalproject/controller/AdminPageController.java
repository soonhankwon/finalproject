package com.backendteam5.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
    // admin detail 새창 띄울때 필요함
    @GetMapping("/detailSearch")
    public String detail(){
        return "detailSearch";
    }

    // admin detail하게 수정하는 창
//    @GetMapping("/detailSave")
//    public String detailSave(){return "detailSave";}
//
//            if (userDetails.getUser().getRole() == UserRoleEnum.ADMIN) {
//        return "index1";
//    } else{
//        model.addAttribute("username", userDetails.getUsername());
//        return "redirect:/api/search/user/courier?state=0";
//    }
}
