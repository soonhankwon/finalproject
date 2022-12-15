package com.backendteam5.finalproject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CountUserDto {
    private String username;
    private String state;
    private Long count;
    @QueryProjection
    public CountUserDto(String username, Long count){
        this.username = username;
        this.count = count;
    }

    @QueryProjection
    public CountUserDto(String username, String state, Long count){
        this.username = username;
        this.state = state;
        this.count = count;
    }
}
