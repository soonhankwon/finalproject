package com.backendteam5.finalproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class AreaIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String area;
    @Column(nullable = false)
    private String route;

    @Column(nullable = false)
    private int subRoute;

    @Column(nullable = false)
    private String zipCode;

    public AreaIndex(String area, String route, int subRoute, String zipCode) {
        this.area = area;
        this.route = route;
        this.subRoute = subRoute;
        this.zipCode = zipCode;
    }
}