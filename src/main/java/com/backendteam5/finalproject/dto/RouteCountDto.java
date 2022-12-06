package com.backendteam5.finalproject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RouteCountDto {
    String route;
    String state;
    Long count;

    @QueryProjection
    public RouteCountDto(String route, String state, Long total){
        this.route = route;
        this.state = state;
        this.count = total;
    }
}
