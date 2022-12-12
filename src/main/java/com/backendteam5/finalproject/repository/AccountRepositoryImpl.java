package com.backendteam5.finalproject.repository;


import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.custom.CustomAccountRepository;
import com.backendteam5.finalproject.repository.custom.CustomAreaIndexRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

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

    @Transactional(readOnly = true)
    @Override
    public List<String> findByAreaAndRole(String area, UserRoleEnum role) {
        return queryFactory
                .select(account.username)
                .from(account)
                .where(account.area.in(area), account.role.eq(role))
                .orderBy(account.username.asc())
                .fetch();
    }
}