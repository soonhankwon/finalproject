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
    private String address;
    private String area;
    private String route;
    private int subRoute;
    private String state;
    private String customer;
    private String arrivalDate;
    private String registerDate;
    private String username;
    private String courierUsername;
    private Double xPos;
    private Double yPos;

    @QueryProjection
    public CourierDto(Long id, String address, String state, String customer, String arrivalDate,String registerDate, String username, Double xPos, Double yPos, DeliveryAssignment deliveryAssignment) {
        this.address = address;
        this.id = id;
        this.registerDate = registerDate;
        this.area = deliveryAssignment.getAreaIndex().getArea();
        this.route = deliveryAssignment.getAreaIndex().getRoute();
        this.subRoute = deliveryAssignment.getAreaIndex().getSubRoute();
        this.state = state;
        this.customer = customer;
        this.arrivalDate = arrivalDate;
        this.username = username;
        this.courierUsername = deliveryAssignment.getAccount().getUsername();
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
