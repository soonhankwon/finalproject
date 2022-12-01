package com.backendteam5.finalproject.entity;

import com.backendteam5.finalproject.dto.CourierReqUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String state;
    private String customer;
    private String arrivalDate;
    private String registerDate;
    private double xPos;
    private double yPos;
    private String username = "ADMIN";

    @ManyToOne
    private DeliveryAssignment deliveryAssignment;

    public Courier(String state, String customer, String arrivalDate, String registerDate ,Double xpos, Double ypos, DeliveryAssignment deliveryAssignment) {
        this.registerDate = registerDate;
        this.xPos = xpos;
        this.yPos = ypos;
        this.deliveryAssignment = deliveryAssignment;
        this.state = state;
        this.customer = customer;
        this.arrivalDate = arrivalDate;
    }

//    public void update(CourierReqUpdateDto courierReqUpdateDto) {
//        this.state = courierReqUpdateDto.getState();
//        this.arrivalDate = courierReqUpdateDto.getArrivalDate();
//        this.username = courierReqUpdateDto.getUsername();
//    }

    public void setUpdate(int j, String username) {
        this.username = username;
    }
    public void saveUpdate(String state, String username) {
        this.state = state;
        this.username = username;
    }
}
