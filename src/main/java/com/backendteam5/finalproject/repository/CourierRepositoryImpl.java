package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.dto.CountDirect;
import com.backendteam5.finalproject.dto.CourierDto;
import com.backendteam5.finalproject.dto.QCourierDto;
import com.backendteam5.finalproject.entity.*;
import com.backendteam5.finalproject.repository.custom.CustomCourierRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.backendteam5.finalproject.entity.QAreaIndex.areaIndex;
import static com.backendteam5.finalproject.entity.QCourier.*;
import static com.backendteam5.finalproject.entity.QDeliveryAssignment.*;

public class CourierRepositoryImpl implements CustomCourierRepository {

    private final JPAQueryFactory queryFactory;
    public CourierRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 배송 상태별 조회
    @Override
    public List<CourierDto> searchByUsernameAndState(Account account, String state) {
        return queryFactory
                .select(getCourierConstructor())
                .from(courier)
                .join(courier.deliveryAssignment, deliveryAssignment)
                .where(usernameEq(account), stateEq(state))
                .orderBy(courier.arrivalDate.desc())
                .fetch();
    }

    // 배송상태별 택배 개수
    @Override
    public Long countUsernameAndState(Account account, String state) {
        return queryFactory
                .select(courier.count())
                .from(courier)
                .join(courier.deliveryAssignment, deliveryAssignment)
                .where(usernameEq(account), stateEq(state))
                .fetchOne();
    }

    // 수령인 이름으로 택배 조회
    @Override
    public List<CourierDto> searchCustomer(String customer) {
        return queryFactory
                .select(getCourierConstructor())
                .from(courier)
                .where(customerEq(customer))
                .fetch();
    }

    @Override
    public List<RouteCountDto> countRouteState(String area, String date) {
        return queryFactory
                .select(getRouteCountDto())
                .from(courier)
                .leftJoin(courier.deliveryAssignment.areaIndex, areaIndex)
                .where(areaIndex.area.eq(area), courier.arrivalDate.eq(date))
                .groupBy(areaIndex.route, courier.state)
                .fetch();
    }

    @Override
    public List<CountDirect> countUsernameDirect(Account account, String date) {
        return queryFactory
                .select(getCountDirect())
                .from(courier)
                .where(courier.deliveryPerson.eq(account.getUsername()),
                        courier.arrivalDate.eq(date))
                .groupBy(courier.state)
                .fetch();
    }

    @Override
    public Long countUsernameTemp(Account account, String date) {
        return queryFactory
                .selectFrom(courier)
                .where(
                        courier.deliveryAssignment.in(
                                JPAExpressions
                                        .select(deliveryAssignment)
                                        .from(deliveryAssignment)
                                        .where(deliveryAssignment.account.eq(account))
                        ),
                        courier.arrivalDate.eq(date),
                        courier.deliveryPerson.ne(account.getUsername()))
                .fetchCount();
    }

    public ConstructorExpression<RouteCountDto> getRouteCountDto(){
        return Projections.constructor(RouteCountDto.class,
                areaIndex.route.as("route"),
                courier.state.as("state"),
                courier.state.count().as("count"));
    }

    public ConstructorExpression<CountDirect> getCountDirect(){
        return Projections.constructor(CountDirect.class,
                courier.state.as("state"),
                courier.state.count().as("count"));
    }

    private static QCourierDto getCourierConstructor() {
        return new QCourierDto(
                courier.id,
                courier.state,
                courier.customer,
                courier.arrivalDate,
                courier.registerDate,
                courier.deliveryPerson,
                deliveryAssignment
        );
    }
    @Modifying(clearAutomatically = true)
    @Transactional
    public void updateByCourierId(Long courierId, String deliveryPerson) {
        long execute = queryFactory
                .update(courier)
                .set(courier.deliveryPerson, deliveryPerson)
                .where(courier.id.eq(courierId))
                .execute();
    }

    private BooleanExpression customerEq(String customer) {
        return courier.customer.eq(customer);
    }

    private BooleanExpression usernameEq(Account account) {
        return deliveryAssignment.account.eq(account);
    }

    private BooleanExpression stateEq(String state) {
        return courier.state.eq(state);
    }
}