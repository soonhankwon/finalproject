package com.backendteam5.finalproject.dto;


import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.AreaIndex;
import com.backendteam5.finalproject.entity.DeliveryAssignment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Setter @Getter
public class CourierDto {

    private Long id;
    private String area;
    private String route;
    private int subRoute;
    private String state;
    private String customer;
    private String arrivalDate;
    private String username;
    private List<Account> accountList;

    @QueryProjection
    public CourierDto(Long id, String state, String customer, String arrivalDate, String username, AreaIndex areaIndex, DeliveryAssignment deliveryAssignment) {
        this.id = id;
        this.route = areaIndex.getRoute();
        this.subRoute = areaIndex.getSubRoute();
        this.state = state;
        this.customer = customer;
        this.arrivalDate = arrivalDate;
        this.username = username;
        this.accountList = deliveryAssignment.getAccount();
    }
}
