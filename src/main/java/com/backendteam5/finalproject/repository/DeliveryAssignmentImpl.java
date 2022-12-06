package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.repository.custom.CustomDeliveryAssignmentRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.backendteam5.finalproject.entity.QDeliveryAssignment.deliveryAssignment;

public class DeliveryAssignmentImpl implements CustomDeliveryAssignmentRepository {
    private final JPAQueryFactory queryFactory;
    @Autowired
    EntityManager em;

    public DeliveryAssignmentImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Modifying(clearAutomatically = true)
    @Transactional
    public void updateByUsername(String username) {
        long execute = queryFactory
                .update(deliveryAssignment)
                .set(deliveryAssignment.account.username, username)
                .execute();
    }
}
