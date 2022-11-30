package com.backendteam5.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<AreaIndex> areaIndex = new ArrayList<>();

    // 택배기사는 1명이고 맡을수 있는 구역은 여러개니깐 OneToMany?
    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    public DeliveryAssignment(Account account) {
        this.account = account;
    }
}
