package com.backendteam5.finalproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
public class SetDeliveryPersonReqDto {
    private List<Long> couriers;
    @NotBlank(message = "택배기사가 선택되지 않았습니다.")
    private String username;
}
