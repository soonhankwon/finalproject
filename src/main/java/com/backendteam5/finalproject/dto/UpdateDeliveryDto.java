package com.backendteam5.finalproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdateDeliveryDto {
    private List<String> zipCode;
    private List<String> username;
}
