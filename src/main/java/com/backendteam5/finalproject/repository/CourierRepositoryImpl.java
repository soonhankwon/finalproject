package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.dto.CourierDto;
import com.backendteam5.finalproject.dto.QCourierDto;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.entity.QCourier;
import com.backendteam5.finalproject.repository.custom.CourierRepositoryCustom;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.backendteam5.finalproject.entity.QCourier.*;

public class CourierRepositoryImpl implements CourierRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CourierRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CourierDto> searchByUsernameAndState(String username, Boolean state) {
        return queryFactory
                .select(new QCourierDto(
                        courier.id,
                        courier.route,
                        courier.subRoute,
                        courier.state,
                        courier.customer,
                        courier.arrivalDate,
                        courier.username
                        ))
                .from(courier)
                .where(usernameEq(username), stateEq(state))
                .fetch();
    }

    @Override
    public Long countUsernameAndState(String username, Boolean state) {
        return queryFactory
                .select(courier.count())
                .from(courier)
                .where(usernameEq(username), stateEq(state))
                .fetchOne();
    }

    private BooleanExpression usernameEq(String username) {
        return courier.username.eq(username);
    }

    private BooleanExpression stateEq(Boolean state) {
        return courier.state.eq(state);
    }
}
