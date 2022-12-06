package com.backendteam5.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "areaIndexId")
    private AreaIndex areaIndex;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    public DeliveryAssignment(Account account, AreaIndex areaIndex) {
        this.areaIndex = areaIndex;
        this.account = account;
    }
}
