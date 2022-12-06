package com.backendteam5.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierReqUpdateDto {
    private String state;
    private String arrivalDate;
    private String username;

    public CourierReqUpdateDto (String state, String arrivalDate, String username) {
        this.state = state;
        this.arrivalDate = arrivalDate;
        this.username = username;
    }
}
