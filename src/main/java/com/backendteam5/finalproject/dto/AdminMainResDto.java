package com.backendteam5.finalproject.dto;

import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AdminMainResDto {
    private List<Account> userList;
    private List<Courier> courierList;

    public AdminMainResDto(List<Account> userList, List<Courier> courierList){
        this.userList = userList;
        this.courierList = courierList;
    }
}
