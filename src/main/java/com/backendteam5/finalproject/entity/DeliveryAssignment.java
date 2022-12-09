package com.backendteam5.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "keyword", columnList = "areaIndex_id, account_id")})
public class DeliveryAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "areaIndex_Id")
    private AreaIndex areaIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public DeliveryAssignment(Account account, AreaIndex areaIndex) {
        this.areaIndex = areaIndex;
        this.account = account;
    }
}
