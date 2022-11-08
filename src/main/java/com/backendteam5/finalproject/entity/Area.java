package com.backendteam5.finalproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Area {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String location;

    @Column(nullable = false)
    private String route;

    @Column(nullable = false)
    private Long subRoute;

    public Area(String location, String route, Long subRoute) {
        this.location = location;
        this.route = route;
        this.subRoute = subRoute;
    }
}
