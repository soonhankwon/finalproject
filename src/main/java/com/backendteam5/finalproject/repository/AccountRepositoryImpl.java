package com.backendteam5.finalproject.repository;


import com.backendteam5.finalproject.repository.custom.CustomAccountRepository;
import com.backendteam5.finalproject.repository.custom.CustomAreaIndexRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.backendteam5.finalproject.entity.QAccount.account;

public class AccountRepositoryImpl implements CustomAccountRepository {

    private final JPAQueryFactory queryFactory;
    public AccountRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional(readOnly = true)
    @Override
    public Long findIdByUsername(String username) {
        return queryFactory
                .select(account.id)
                .from(account)
                .where(account.username.eq(username))
                .fetchOne();
    }
}