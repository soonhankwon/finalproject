package com.backendteam5.finalproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(indexes = {@Index(name = "keyword", columnList = "area, route, sub_route, zip_code")})
public class AreaIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "area", length = 50)
    private String area;
    @Column(name = "route", length = 5)
    private String route;

    @Column(name = "sub_route")
    private int subRoute;

    @Column(name = "zip_code", length = 30)
    private String zipCode;

    @Column(name = "difficulty")
    private float difficulty;

    public AreaIndex(String area, String route, int subRoute, String zipCode, float difficulty) {
        this.area = area;
        this.route = route;
        this.subRoute = subRoute;
        this.zipCode = zipCode;
        this.difficulty = difficulty;
    }
}