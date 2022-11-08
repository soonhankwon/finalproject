package com.backendteam5.finalproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class CourierResUpdateDto {
    private String area;
    private Boolean state;
    private String customer;
    private String arrivalDate;
    private String username;

    public CourierResUpdateDto (String area, Boolean state, String customer, String arrivalDate, String username) {
        this.area = area;
        this.state = state;
        this.customer = customer;
        this.arrivalDate = arrivalDate;
        this.username = username;
    }
}
