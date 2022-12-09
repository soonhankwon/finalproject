package com.backendteam5.finalproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SetDeliveryPersonReqDto {
    private List<Long> couriers;
    private String username;
}
