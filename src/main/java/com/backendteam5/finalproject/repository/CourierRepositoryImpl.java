package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.dto.*;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.repository.custom.CustomCourierRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @Transactional(readOnly = true)
    @Override
    public CourierCountDto stateCount(Account account) {
        List<Long> ids = queryFactory
                .select(courier.id)
                .from(courier)
                .where(usernameEq(account))
                .fetch();

        List<Tuple> list = queryFactory
                .select(courier.id.count(), courier.state)
                .from(courier)
                .where(courier.id.in(ids))
                .groupBy(courier.state)
                .fetch();

        Long progressCnt = 0L;
        Long completeCnt = 0L;
        for (Tuple tuple : list) {
            if (tuple.get(1, String.class).equals("배송중")) {
                progressCnt = tuple.get(0, Long.class);
            }
            else {
                completeCnt = tuple.get(0, Long.class);
            }
        }
        return new CourierCountDto(progressCnt, completeCnt);
    }

    // 수령인 이름으로 택배 조회
    @Transactional(readOnly = true)
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

    // div에 들어갈 route별 count
    @Transactional(readOnly = true)
    @Override
    public List<RouteCountDto> countRouteState(String area) {
        return queryFactory
                .select(getRouteCountDto())
                .from(courier)
                .leftJoin(courier.deliveryAssignment.areaIndex, areaIndex)
                .where(areaIndex.area.eq(area), courier.arrivalDate.eq(getNowDate()))
                .groupBy(areaIndex.route, courier.state)
                .orderBy(areaIndex.route.asc(), courier.state.desc())
                .fetch();
    }

    // user테이블에 들어갈 직접 할당의 state별 갯수
    @Transactional(readOnly = true)
    @Override
    public List<CountStateDto> countUsernameDirect(Account account) {
        return queryFactory
                .select(getCountDirect())
                .from(courier)
                .where(courier.deliveryPerson.eq(account.getUsername()),
                        courier.arrivalDate.eq(getNowDate()))
                .groupBy(courier.state)
                .orderBy(courier.state.desc())
                .fetch();
    }

    // user테이블에 들어갈 임시 할당의 갯수
    @Transactional(readOnly = true)
    @Override
    public Long countUsernameTemp(Account account) {
        return queryFactory
                .select(courier.count())
                .from(courier)
                .where(
                        courier.deliveryAssignment.id.in(
                                JPAExpressions
                                        .select(deliveryAssignment.id)
                                        .from(deliveryAssignment)
                                        .where(deliveryAssignment.account.id.eq(account.getId()))
                        ),
                        courier.arrivalDate.eq(getNowDate()),
                        courier.deliveryPerson.ne(account.getUsername()))
                .fetchOne();
    }

    // 상세 조회 기능 Optinal을 true면 직접할당 아니면 임시할당
    @Transactional(readOnly = true)
    @Override
    public List<AdminCourierDto> searchByDetail(String username,String area, SearchReqDto searchReqDto){
        return searchReqDto.getOption() ? searchByDetailDirect(area, searchReqDto) : searchByDetailTemp(username, area, searchReqDto);
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

    @Transactional(readOnly = true)
    public List<AdminCourierDto> searchByDetailTemp(String username,String area, SearchReqDto searchReqDto) {
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
                        dateLoe(searchReqDto),
                        courier.deliveryPerson.eq(username))
                .fetch();
    }

    // Courier의 id로 검색
    @Transactional(readOnly = true)
    @Override
    public List<AdminCourierDto> searchByCouriers(List<Long> couriers){
        return queryFactory
                .select(getAdminCourierDto())
                .from(courier)
                .join(courier.deliveryAssignment, deliveryAssignment)
                .join(deliveryAssignment.areaIndex, areaIndex)
                .join(deliveryAssignment.account, account)
                .where(courier.id.in(couriers))
                .fetch();
    }

    @Modifying(clearAutomatically = true)
    @Transactional
    @Override
    public String setReady() {
        queryFactory
                .update(courier)
                .set(courier.arrivalDate, getNowDate())
                .set(courier.deliveryPerson, "GUROADMIN")
                .where(courier.state.ne("배송완료"),
                        courier.arrivalDate.lt(getNowDate()))
                .execute();

        return "업데이트 완료";
    }

    @Modifying(clearAutomatically = true)
    @Transactional
    @Override
    public String setUpdateStateDelay(List<Long> couriers) {
        queryFactory
                .update(courier)
                .set(courier.state, "배송지연")
                .where(courier.id.in(couriers))
                .execute();

        return "업데이트 완료";
    }

    @Modifying(clearAutomatically = true)
    @Transactional
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

    public ConstructorExpression<CountStateDto> getCountDirect(){
        return Projections.constructor(CountStateDto.class,
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

    private BooleanExpression customerEq(String customer) {return courier.customer.eq(customer);}

    private BooleanExpression usernameEq(Account account) {
        return courier.deliveryAssignment.account.eq(account);
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