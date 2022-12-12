package com.backendteam5.finalproject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CountTempDto {
    private String username;
    private Long count;
    @QueryProjection
    public CountTempDto(String username, Long count){
        this.username = username;
        this.count = count;
    }
}
