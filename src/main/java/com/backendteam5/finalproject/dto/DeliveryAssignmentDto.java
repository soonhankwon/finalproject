package com.backendteam5.finalproject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryAssignmentDto {
    private Long id;
    private int subRoute;
    private String zipCode;
    private float difficulty;
    private Long count;

    @QueryProjection
    public DeliveryAssignmentDto(Long id, int subRoute, String zipCode,
                                 float difficulty, Long count){
        this.id = id;
        this.subRoute = subRoute;
        this.zipCode = zipCode;
        this.difficulty = difficulty;
        this.count = count;
    }
}
