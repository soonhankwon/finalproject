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

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double xPos;

    @Column(nullable = false)
    private double yPos;

    public AreaIndex(String area, String route, int subRoute, String zipCode, String address, double xPos, double yPos) {
        this.area = area;
        this.route = route;
        this.subRoute = subRoute;
        this.zipCode = zipCode;
        this.address = address;
        this.xPos = xPos;
        this.yPos = yPos;
    }
}