package com.backendteam5.finalproject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class CountDirectDto {
    private String state;
    private Long count;

    @QueryProjection
    public CountDirectDto(String state, Long count){
        this.count = count;
        this.state = state;
    }
}
