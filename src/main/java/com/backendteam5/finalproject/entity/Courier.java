package com.backendteam5.finalproject.entity;

import com.backendteam5.finalproject.dto.CourierReqUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "keyword", columnList = "deliveredDate, state, customer")})
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "address")
    @NotNull
    private String address;
    @Column(name = "state", length = 25)
    @NotNull
    private String state;
    @Column(name = "customer", length = 100)
    private String customer;
    @Column(name = "arrival_date", length = 100)
    private String arrivalDate;
    @Column(name = "register_date", length = 100)
    private String registerDate;
    @Column(name = "x_pos")
    private double xPos;
    @Column(name = "y_pos")
    private double yPos;
    @Column(name = "deliveryPerson", length = 100)
    @NotNull
    private String deliveryPerson = "GUROADMIN";
    @Column(name = "deliveredDate", length = 45)
    private String deliveredDate = "배송전";
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "delivery_assignment_id")
    private DeliveryAssignment deliveryAssignment;


    public Courier(String state, String customer, String arrivalDate, String registerDate ,
                   Double xpos, Double ypos, DeliveryAssignment deliveryAssignment, String deliveredDate) {
        this.registerDate = registerDate;
        this.xPos = xpos;
        this.yPos = ypos;
        this.deliveryAssignment = deliveryAssignment;
        this.state = state;
        this.customer = customer;
        this.arrivalDate = arrivalDate;
        this.deliveredDate = deliveredDate;
    }

    public void update(CourierReqUpdateDto courierReqUpdateDto) {
        this.state = courierReqUpdateDto.getState();
        this.arrivalDate = courierReqUpdateDto.getArrivalDate();
        this.deliveryPerson = courierReqUpdateDto.getDeliveryPerson();
    }

    public void setUpdate(int j, String deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }
    public void saveUpdate(String state, String deliveryPerson) {
        this.state = state;
        this.deliveryPerson = deliveryPerson;
    }
}
