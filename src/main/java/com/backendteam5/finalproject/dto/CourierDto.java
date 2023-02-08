package com.backendteam5.finalproject.dto;


import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.entity.DeliveryAssignment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter @Getter
public class CourierDto {

    private Long id;
    private String address;
    private String area;
    private String route;
    private int subRoute;
    private Courier.State state;
    private String customer;
    private String arrivalDate;
    private String registerDate;
    private String username;
    private String courierUsername;
    private String deliveredDate;

    @QueryProjection
    public CourierDto(Long id, String address, Courier.State state, String customer, String arrivalDate, String registerDate, String username, String deliveredDate, DeliveryAssignment deliveryAssignment) {
        this.address = address;
        this.deliveredDate = deliveredDate;
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
    }
}