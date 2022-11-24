package com.backendteam5.finalproject.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourierDto {

    private Long id;
    private String route;
    private int subRoute;
    private Boolean state;
    private String customer;
    private String arrivalDate;
    private String username;

    @QueryProjection
    public CourierDto(Long id, String route, int subRoute, Boolean state, String customer, String arrivalDate, String username) {
        this.id = id;
        this.route = route;
        this.subRoute = subRoute;
        this.state = state;
        this.customer = customer;
        this.arrivalDate = arrivalDate;
        this.username = username;
    }

    public void setUpdate(int j, String username) {
        this.username = username;
    }
}
