package com.backendteam5.finalproject.entity;

import com.backendteam5.finalproject.dto.CourierReqUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "keyword", columnList = "address, state, customer")})
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "state", length = 25, nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "customer", length = 100)
    private String customer;

    @Column(name = "arrival_date", length = 100)
    private String arrivalDate;

    @Column(name = "register_date", length = 100)
    private String registerDate;

    //TODO ddl의 default 값을 바꿀것
    @Column(name = "deliveryPerson", length = 100, nullable = false)
    private String deliveryPerson = "GUROADMIN";

    @Column(name = "deliveredDate", length = 45)
    private String deliveredDate = "배송전";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_assignment_id")
    private DeliveryAssignment deliveryAssignment;

    public enum State {
        SHIPPING, DELIVERED, DELAYED
    }

    public void update(CourierReqUpdateDto courierReqUpdateDto) {
        this.state = courierReqUpdateDto.getState();
        this.arrivalDate = courierReqUpdateDto.getArrivalDate();
        this.deliveryPerson = courierReqUpdateDto.getDeliveryPerson();
    }

    public void saveUpdate(State state, String deliveryPerson, String deliveredDate) {
        this.deliveredDate = deliveredDate;
        this.state = state;
        this.deliveryPerson = deliveryPerson;
    }

    public void updateArrivalDateAndDeliveredDate(String arrivalDate, String deliveredDate) {
        this.arrivalDate = arrivalDate;
        this.deliveredDate = deliveredDate;
    }
}
