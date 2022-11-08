package com.backendteam5.finalproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CourierInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String route;

    @Column(nullable = false)
    private Long subRoute;

    @Column(nullable = false)
    private String location;

    public CourierInfo(String route, Long subRoute, String location){
        this.route = route;
        this.subRoute = subRoute;
        this.location = location;
    }
}
