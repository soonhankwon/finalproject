package com.backendteam5.finalproject.dto;

import com.backendteam5.finalproject.entity.DeliveryAssignment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierReqUpdateDto {
    private Boolean state;
    private String arrivalDate;
    private DeliveryAssignment deliveryAssignment;

    public CourierReqUpdateDto (Boolean state, String arrivalDate, DeliveryAssignment deliveryAssignment) {
        this.state = state;
        this.arrivalDate = arrivalDate;
        this.deliveryAssignment = deliveryAssignment;
    }
}
