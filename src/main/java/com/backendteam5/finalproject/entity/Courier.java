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
    private String address;
    private String registerDate;
    private String arrivalDate;
    private String customer;
    private String state;
    private String deliveryPerson = "ADMIN";
    private double xPos;
    private double yPos;

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

    public void update(CourierReqUpdateDto courierReqUpdateDto) {
        this.state = courierReqUpdateDto.getState();
        this.arrivalDate = courierReqUpdateDto.getArrivalDate();
        this.deliveryPerson = courierReqUpdateDto.getUsername();
    }

    public void setUpdate(int j, String username) {
        this.deliveryPerson = username;
    }
    public void saveUpdate(String state, String username) {
        this.state = state;
        this.deliveryPerson = username;
    }
}
