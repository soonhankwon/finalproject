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
                           String area, String route, int subroute, String tempPerson,
                           String address, double xPos, double yPos) {
        this.id = id;
        this.address = address;
        this.area = area;
        this.route = route;
        this.subRoute = subroute;
        this.registerDate = registerDate;
        this.arrivalDate = arrivalDate;
        this.customer = customer;
        this.state = state;
        this.deliveryPerson = deliveryPerson;
        this.tempPerson = tempPerson;
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
