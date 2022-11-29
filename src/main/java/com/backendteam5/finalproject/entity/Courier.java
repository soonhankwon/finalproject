package com.backendteam5.finalproject.entity;

import com.backendteam5.finalproject.dto.CourierReqUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String route;
    private int subRoute;
    private Boolean state;
    private String customer;
    private String arrivalDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username_id", nullable = false)
    private DeliveryAssignment deliveryAssignment;

    public Courier(String state, String customer, String arrivalDate, DeliveryAssignment deliveryAssignment) {
        this.state = state;
        this.customer = customer;
        this.arrivalDate = arrivalDate;
        this.deliveryAssignment = deliveryAssignment;
    }

    public void update(CourierReqUpdateDto courierReqUpdateDto) {
        this.state = courierReqUpdateDto.getState();
        this.arrivalDate = courierReqUpdateDto.getArrivalDate();
        this.username = courierReqUpdateDto.getUsername();
    }
    public void setUpdate(int j, String username) {
        this.username = username;
    }
}
