package com.backendteam5.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class CourierReqUpdateDto {
    @NotBlank(message = "state가 비어있습니다.")
    private String state;
    @NotBlank(message = "arrivalDate가 비어있습니다.")
    private String arrivalDate;
    private String deliveryPerson;

    public CourierReqUpdateDto (String state, String arrivalDate, String deliveryPerson) {
        this.state = state;
        this.arrivalDate = arrivalDate;
        this.deliveryPerson = deliveryPerson;
    }

    public void checkState(){
        List<String> states = Arrays.asList("배송중", "배송지연", "배송완료");
        if(!states.contains(this.state)) throw new IllegalArgumentException("state의 값에 이상이 있습니다.");
    }
}
