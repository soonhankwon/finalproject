package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.dto.CourierDto;
import com.backendteam5.finalproject.dto.QCourierDto;
import com.backendteam5.finalproject.repository.custom.CustomCourierRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.backendteam5.finalproject.entity.QCourier.*;

public class CourierRepositoryImpl implements CustomCourierRepository {

    private final JPAQueryFactory queryFactory;
    @Autowired
    EntityManager em;

    public CourierRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 배송 상태별 조회
    @Override
    public List<CourierDto> searchByUsernameAndState(String username, Boolean state) {
        return queryFactory
                .select(getCourierConstructor())
                .from(courier)
                .where(usernameEq(username), stateEq(state))
                .orderBy(courier.arrivalDate.desc())
                .fetch();
    }

    // 배송상태별 택배 개수
    @Override
    public Long countUsernameAndState(String username, Boolean state) {
        return queryFactory
                .select(courier.count())
                .from(courier)
                .where(usernameEq(username), stateEq(state))
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

    public List<CourierDto> findByRouteAndSubRoute(String route, int subRoute) {
        return  queryFactory
                .select(getCourierConstructor())
                .from(courier)
                .where(courier.route.eq(route), courier.subRoute.eq(subRoute))
                .fetch();
    }

    @Modifying(clearAutomatically = true)
    @Transactional
    public void updateByCourierId(Long courierId, String username) {
        long execute = queryFactory
                .update(courier)
                .set(courier.deliveryAssignment.username, username)
                .where(courier.id.eq(courierId))
                .execute();
    }

    private static QCourierDto getCourierConstructor() {
        return new QCourierDto(
                courier.id,
                courier.route,
                courier.subRoute,
                courier.state,
                courier.customer,
                courier.arrivalDate,
                courier.deliveryAssignment.username
        );
    }

    private BooleanExpression customerEq(String customer) {
        return courier.customer.eq(customer);
    }

    private BooleanExpression usernameEq(String username) {
        return courier.deliveryAssignment.username.eq(username);
    }

    private BooleanExpression stateEq(Boolean state) {
        return courier.state.eq(state);
    }
}
