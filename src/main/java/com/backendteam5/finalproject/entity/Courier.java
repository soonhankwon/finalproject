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
    @Column(name = "address")
    private String address;
    @Column(name = "state")
    private String state;
    @Column(name = "customer")
    private String customer;
    @Column(name = "arrival_date")
    private String arrivalDate;
    @Column(name = "register_date")
    private String registerDate;
    @Column(name = "x_pos")
    private double xPos;
    @Column(name = "y_pos")
    private double yPos;
    @Column(name = "username")
    private String username = "ADMIN";

    @ManyToOne
    @JoinColumn(name = "delivery_assignment_id")
    private DeliveryAssignment deliveryAssignment;

    public Courier(String state, String address, String customer, String arrivalDate, String registerDate ,Double xpos, Double ypos, DeliveryAssignment deliveryAssignment) {
        this.address = address;
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
