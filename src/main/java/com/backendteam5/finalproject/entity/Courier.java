package com.backendteam5.finalproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;


@Entity
@Getter
@NoArgsConstructor
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String area;
    private Boolean state;
    private String customer;
    private String arrivalDate;
    private String username;

    public Courier(String area, Boolean state, String customer, String arrivalDate, String username) {
        this.area = area;
        this.state = state;
        this.customer = customer;
        this.arrivalDate = arrivalDate;
        this.username = username;
    }
}
