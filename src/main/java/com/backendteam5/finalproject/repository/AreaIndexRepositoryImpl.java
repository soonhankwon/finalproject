package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.repository.custom.CustomAreaIndexRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static com.backendteam5.finalproject.entity.QAreaIndex.areaIndex;

public class AreaIndexRepositoryImpl implements CustomAreaIndexRepository {

    private final JPAQueryFactory queryFactory;
    public AreaIndexRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Transactional(readOnly = true)
    @Override
    public List<Long> findIdByzipCode(List<String> zipcode) {
        return queryFactory
                .select(areaIndex.id)
                .from(areaIndex)
                .where(areaIndex.zipCode.in(zipcode))
                .fetch();
    }
}