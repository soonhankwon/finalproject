package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.dto.*;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.repository.custom.CustomCourierRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.backendteam5.finalproject.entity.QAreaIndex.areaIndex;
import static com.backendteam5.finalproject.entity.QCourier.courier;
import static com.backendteam5.finalproject.entity.QDeliveryAssignment.deliveryAssignment;

public class CourierRepositoryImpl implements CustomCourierRepository {

    private final JPAQueryFactory queryFactory;
    public CourierRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }



    // 배송 상태별 조회
    @Override
    @Transactional(readOnly = true)
    public List<CourierDto> searchByUsernameAndState(Account account, String state, String username) {
        List<Long> ids = queryFactory
                .select(courier.id)
                .from(courier)
                .innerJoin(courier.deliveryAssignment, deliveryAssignment)
                .where(usernameEq(account), stateEq(state), stateUsernameEq(username))
                .fetch();

        return queryFactory
                .select(getCourierConstructor())
                .from(courier)
                .where(courier.id.in(ids))
                .fetch();
    }

    // 배송상태별 택배 개수
    @Override
    public List<Long> stateCount(Account account) {
        List<Long> ids = queryFactory
                .select(courier.id)
                .from(courier)
                .where(usernameEq(account))
                .fetch();

        return queryFactory
                .select(courier.id.count())
                .from(courier)
                .where(courier.id.in(ids))
                .groupBy(courier.state)
                .fetch();
    }

    // 수령인 이름으로 택배 조회
    @Override
    public List<CourierDto> searchCustomer(String customer) {
        List<Long> ids = queryFactory
                .select(courier.id)
                .from(courier)
                .where(customerEq(customer))
                .fetch();

        return queryFactory
                .select(getCourierConstructor())
                .from(courier)
                .where(courier.id.in(ids))
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
    public List<CountDirectDto> countUsernameDirect(Account account, String date) {
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
                        courier.deliveryAssignment.id.in(
                                JPAExpressions
                                        .select(deliveryAssignment.id)
                                        .from(deliveryAssignment)
                                        .where(deliveryAssignment.account.id.eq(account.getId()))
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

    public ConstructorExpression<CountDirectDto> getCountDirect(){
        return Projections.constructor(CountDirectDto.class,
                courier.state.as("state"),
                courier.state.count().as("count"));
    }

    private static QCourierDto getCourierConstructor() {
        return new QCourierDto(
                courier.id,
                courier.address,
                courier.state,
                courier.customer,
                courier.arrivalDate,
                courier.registerDate,
                courier.deliveryPerson,
                courier.deliveryAssignment
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

    private BooleanExpression stateUsernameEq(String username) {
        return courier.deliveryPerson.eq(username);
    }

    private BooleanExpression customerEq(String customer) {
        return courier.customer.eq(customer);
    }

    private BooleanExpression usernameEq(Account account) {
        return courier.deliveryAssignment.account.eq(account);
    }

    private BooleanExpression stateEq(String state) {
        return courier.state.eq(state);
    }

    //위치 AdminService로 옴기기
    private String convertNowDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }
}