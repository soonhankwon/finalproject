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

    @Column(name = "area")
    private String area;
    @Column(name = "route")
    private String route;

    @Column(name = "sub_route")
    private int subRoute;

    @Column(name = "zip_code")
    private String zipCode;


    public AreaIndex(String area, String route, int subRoute, String zipCode) {
        this.area = area;
        this.route = route;
        this.subRoute = subRoute;
        this.zipCode = zipCode;
    }
}