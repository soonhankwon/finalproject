package com.backendteam5.finalproject.repository;


import com.backendteam5.finalproject.dto.CountUserDto;
import com.backendteam5.finalproject.dto.UserDto;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.custom.CustomAccountRepository;
import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static com.backendteam5.finalproject.entity.QAccount.account;
import static com.backendteam5.finalproject.entity.QCourier.courier;

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
    public List<UserDto> findByAreaAndRole(String area, UserRoleEnum role) {
        return queryFactory
                .select(getUserDto())
                .from(account)
                .where(account.area.in(area), account.role.eq(role))
                .orderBy(account.username.asc())
                .fetch();
    }

    public ConstructorExpression<UserDto> getUserDto(){
        return Projections.constructor(UserDto.class,
                account.username.as("username"),
                account.capacity.as("capacity")
        );
    }
}