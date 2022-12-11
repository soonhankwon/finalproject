package com.backendteam5.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "keyword", columnList = "areaIndexId, accountId")})
public class DeliveryAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "areaIndex_id", unique = true, nullable = false)
    private AreaIndex areaIndex;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public DeliveryAssignment(Account account, AreaIndex areaIndex) {
        this.areaIndex = areaIndex;
        this.account = account;
    }
}
