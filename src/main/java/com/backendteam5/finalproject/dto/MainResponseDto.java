package com.backendteam5.finalproject.dto;

import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MainResponseDto {
    private List<Account> userlist;
    private List<Courier> courierList;

    public MainResponseDto(List<Account> userlist, List<Courier> courierList){
        this.userlist = userlist;
        this.courierList = courierList;
    }
}
