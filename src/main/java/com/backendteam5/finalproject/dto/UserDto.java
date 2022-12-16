package com.backendteam5.finalproject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String username;
    private short capacity;

    @QueryProjection
    public UserDto(String username, short capacity){
        this.username = username;
        this.capacity = capacity;
    }
}
