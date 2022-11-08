package com.backendteam5.finalproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    private String area;
    private Boolean state;
    private String customer;
    private String arrivalDate;
//    private String username;

    public Courier(String area, Boolean state, String customer, String arrivalDate) {
        this.area = area;
        this.state = state;
        this.customer = customer;
        this.arrivalDate = arrivalDate;
//        this.username = username;
    }
}
