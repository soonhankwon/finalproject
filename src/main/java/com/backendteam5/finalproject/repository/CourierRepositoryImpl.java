package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.dto.*;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.repository.custom.CustomCourierRepository;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.backendteam5.finalproject.entity.QAccount.account;
import static com.backendteam5.finalproject.entity.QAreaIndex.areaIndex;
import static com.backendteam5.finalproject.entity.QCourier.courier;
import static com.backendteam5.finalproject.entity.QDeliveryAssignment.deliveryAssignment;
import static org.springframework.util.StringUtils.hasText;


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

    //
    @Override
    public List<RouteCountDto> countRouteState(String area) {
        return queryFactory
                .select(getRouteCountDto())
                .from(courier)
                .leftJoin(courier.deliveryAssignment.areaIndex, areaIndex)
                .where(areaIndex.area.eq(area), courier.arrivalDate.eq(getNowDate()))
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

    @Override
    public List<AdminCourierDto> searchByDetail(String area, SearchReqDto searchReqDto){
        return searchReqDto.getOption() ? searchByDetailDirect(area, searchReqDto) : searchByDetailTemp(area, searchReqDto);
    }

    public List<AdminCourierDto> searchByDetailDirect(String area, SearchReqDto searchReqDto) {
        return queryFactory
                .select(getAdminCourierDto())
                .from(courier)
                .join(courier.deliveryAssignment, deliveryAssignment)
                .join(deliveryAssignment.areaIndex, areaIndex)
                .join(deliveryAssignment.account, account)
                .where(areaIndex.area.eq(area),
                        routeEq(searchReqDto),
                        subRouteIn(searchReqDto),
                        stateEq(searchReqDto),
                        deliveryPersonEq(searchReqDto),
                        dateLoe(searchReqDto))
                .fetch();
    }

    public List<AdminCourierDto> searchByDetailTemp(String area, SearchReqDto searchReqDto) {
        return queryFactory
                .select(getAdminCourierDto())
                .from(courier)
                .join(courier.deliveryAssignment, deliveryAssignment)
                .join(deliveryAssignment.areaIndex, areaIndex)
                .join(deliveryAssignment.account, account)
                .where(deliveryAssignment.areaIndex.area.eq(area),
                        routeEq(searchReqDto),
                        subRouteIn(searchReqDto),
                        stateEq(searchReqDto),
                        tempPersonEq(searchReqDto),
                        dateLoe(searchReqDto))
                .fetch();
    }

    @Override
    public String setArrivalDate(List<String> zipCode) {
        queryFactory
                .update(courier)
                .set(courier.arrivalDate, getNowDate())
                .where(courier.deliveryAssignment.id.in(
                        JPAExpressions
                                .select(deliveryAssignment.id)
                                .from(deliveryAssignment)
                                .join(deliveryAssignment.areaIndex, areaIndex)
                                        .on(areaIndex.zipCode.in(zipCode))
                        ))
                .execute();

        return "업데이트 완료";
    }

    @Override
    public String setUpdateState(List<Long> couriers) {
        queryFactory
                .update(courier)
                .set(courier.state, "배송지연")
                .where(courier.id.in(couriers))
                .execute();

        return "업데이트 완료";
    }

    @Override
    public String setDeliveryPerson(List<Long> couriers, String username) {
        queryFactory
                .update(courier)
                .set(courier.deliveryPerson,
                        JPAExpressions
                                .select(account.username)
                                .from(account)
                                .where(account.username.eq(username)))
                .where(courier.id.in(couriers))
                .execute();

        return "업데이트 완료";
    }


    public ConstructorExpression<AdminCourierDto> getAdminCourierDto(){
        return Projections.constructor(AdminCourierDto.class,
                courier.id,
                courier.registerDate,
                courier.arrivalDate,
                courier.customer,
                courier.state,
                courier.deliveryPerson,
                areaIndex,
                account,
                courier.address,
                courier.xPos,
                courier.yPos);
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
                courier.state,
                courier.customer,
                courier.arrivalDate,
                courier.registerDate,
                courier.deliveryPerson,
                deliveryAssignment
        );
    }

    private BooleanExpression customerEq(String customer) {return courier.customer.eq(customer);}

    private BooleanExpression usernameEq(Account account) {
        return deliveryAssignment.account.eq(account);
    }

    private BooleanExpression stateEq(String state) {return courier.state.eq(state);}

    private BooleanExpression stateEq(SearchReqDto searchReqDto) {
        return hasText(searchReqDto.getState()) ? courier.state.eq(searchReqDto.getState()) : null;
    }

    private BooleanExpression deliveryPersonEq(SearchReqDto searchReqDto){
        return hasText(searchReqDto.getUsername()) ? courier.deliveryPerson.eq(searchReqDto.getUsername()) : null;
    }

    private BooleanExpression tempPersonEq(SearchReqDto searchReqDto){
        return hasText(searchReqDto.getUsername()) ? deliveryAssignment.account.username.eq(searchReqDto.getUsername()) : null;
    }

    private BooleanExpression routeEq(SearchReqDto searchReqDto){
        return hasText(searchReqDto.getRoute()) ? deliveryAssignment.areaIndex.route.eq(searchReqDto.getRoute()) : null;
    }

    private BooleanExpression subRouteIn(SearchReqDto searchReqDto){
        return searchReqDto.getSubRoute() == null || searchReqDto.getSubRoute().isEmpty() ? null : deliveryAssignment.areaIndex.subRoute.in(searchReqDto.getSubRoute());
    }

    private BooleanExpression dateLoe(SearchReqDto searchReqDto){
        return searchReqDto.getCurrentDay() == 0 ? null : courier.registerDate.loe(searchReqDto.getDate());
    }

    @Override
    public String getNowDate(){
        Calendar cal = SearchReqDto.getNow();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(cal.getTime());
    }
}