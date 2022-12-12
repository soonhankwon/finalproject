package com.backendteam5.finalproject.dto;

import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.AreaIndex;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class AdminCourierDto {
    private Long id;
    private String address;
    private String area;
    private String route;
    private int subRoute;
    private String registerDate;
    private String arrivalDate;
    private String customer;
    private String state;
    private String deliveryPerson;
    private String tempPerson;
    private double xPos;
    private double yPos;

    @QueryProjection
    public AdminCourierDto(Long id, String registerDate, String arrivalDate,
                           String customer, String state, String deliveryPerson,
                           AreaIndex areaIndex, Account account,
                           String address, double xPos, double yPos) {
        this.id = id;
        this.address = address;
        this.area = areaIndex.getArea();
        this.route = areaIndex.getRoute();
        this.subRoute = areaIndex.getSubRoute();
        this.registerDate = registerDate;
        this.arrivalDate = arrivalDate;
        this.customer = customer;
        this.state = state;
        this.deliveryPerson = deliveryPerson;
        this.tempPerson = account.getUsername();
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
