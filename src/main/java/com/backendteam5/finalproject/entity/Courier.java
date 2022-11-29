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
//    private String area;
//    private String route;
//    private String subRoute;
    private String state;
    private String customer;
    private String registerDate;
    private String arrivalDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username_id", nullable = false)
    private DeliveryAssignment deliveryAssignment;

    public Courier(String route, int subRoute, Boolean state, String customer, String arrivalDate, DeliveryAssignment deliveryAssignment) {
        this.route = route;
        this.subRoute = subRoute;
        this.state = state;
        this.customer = customer;
        this.arrivalDate = arrivalDate;
        this.deliveryAssignment = deliveryAssignment;
    }

    public void update(CourierReqUpdateDto courierReqUpdateDto) {
        this.state = courierReqUpdateDto.getState();
        this.arrivalDate = courierReqUpdateDto.getArrivalDate();
        this.deliveryAssignment = courierReqUpdateDto.getDeliveryAssignment();
    }
}
