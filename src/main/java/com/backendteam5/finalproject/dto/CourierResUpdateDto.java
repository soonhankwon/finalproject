package com.backendteam5.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierResUpdateDto {
    private String msg;

    public CourierResUpdateDto(String msg) {
        this.msg = msg;
    }
}
