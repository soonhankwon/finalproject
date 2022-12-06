package com.backendteam5.finalproject.dto;

import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.DeliveryAssignment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierReqUpdateDto {
    private String state;
    private String arrivalDate;
    private String deliveryPerson;

    public CourierReqUpdateDto (String state, String arrivalDate, String deliveryPerson) {
        this.state = state;
        this.arrivalDate = arrivalDate;
        this.deliveryPerson = deliveryPerson;
    }
}
