package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.dto.CourierDto;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.repository.custom.CustomCourierRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourierRepository extends JpaRepository<Courier, Long>, CustomCourierRepository {
//    List<Courier> findBySubRoute(int subRoute);
//    List<Courier> findByRoute(String route);
//    List<Courier> findByRouteAndUsernameAndSubRouteAndStateOrderByArrivalDateAsc (String route, String username,
//                                                                                  int SubRoute, Boolean state);
//    //username이 있음, subRoute 있음, state 있음, 올림
//    List<Courier> findByRouteAndUsernameAndSubRouteAndStateOrderByArrivalDateDesc (String route, String username,
//                                                                                   int SubRoute, Boolean state);
//    //username이 있음, subRoute 있음, state 있음, 내림
//    List<Courier> findByRouteAndUsernameAndSubRouteOrderByArrivalDateAsc (String route, String username,
//                                                                          int SubRoute);
//    //username이 있음, subRoute 있음, state 없음, 올림
//    List<Courier> findByRouteAndUsernameAndSubRouteOrderByArrivalDateDesc (String route, String username,
//                                                                           int SubRoute);
//    //username이 있음, subRoute 있음, state 없음, 내림
//    //============================================================================================
//    List<Courier> findByRouteAndUsernameAndStateOrderByArrivalDateAsc (String route, String username,
//                                                                       Boolean state);
//    //username이 있음, subRoute 없음, state 있음, 올림
//    List<Courier> findByRouteAndUsernameAndStateOrderByArrivalDateDesc (String route, String username,
//                                                                        Boolean state);
//    //username이 있음, subRoute 없음, state 있음, 내림
//    List<Courier> findByRouteAndUsernameOrderByArrivalDateAsc (String route, String username);
//    //username이 있음, subRoute 없음, state 없음, 올림
//    List<Courier> findByRouteAndUsernameOrderByArrivalDateDesc (String route, String username);
//    //username이 있음, subRoute 없음, state 없음, 내림
//    //===================================================================================
//    List<Courier> findByRouteAndSubRouteAndStateOrderByArrivalDateAsc (String route, int SubRoute, Boolean state);
//    //username이 없음, subRoute 있음, state 있음, 올림
//    List<Courier> findByRouteAndSubRouteAndStateOrderByArrivalDateDesc (String route, int SubRoute, Boolean state);
//    //username이 없음, subRoute 있음, state 있음, 내림
//    List<Courier> findByRouteAndSubRouteOrderByArrivalDateAsc (String route, int SubRoute);
//    //username이 없음, subRoute 있음, state 없음, 올림
//    List<Courier> findByRouteAndSubRouteOrderByArrivalDateDesc (String route, int SubRoute);
//    //username이 없음, subRoute 있음, state 없음, 내림
//    List<Courier> findByRouteAndStateOrderByArrivalDateAsc (String route, Boolean state);
//    //username이 없음, subRoute 없음, state 있음, 올림
//    List<Courier> findByRouteAndStateOrderByArrivalDateDesc (String route, Boolean state);
//    //username이 없음, subRoute 없음, state 있음, 내림
//    List<Courier> findByRouteOrderByArrivalDateAsc (String route);
//    //username이 없음, subRoute 없음, state 없음, 올림
//    List<Courier> findByRouteOrderByArrivalDateDesc (String route);
//    //username이 없음, subRoute 없음, state 없음, 내림
//
//    Integer countCourierByUsername(String username);
//    List<Courier> findByUsernameAndStateOrderByArrivalDateDesc(String username, Boolean state);
//    Long countByUsernameAndState(String username, Boolean state);
//    List<CourierDto> findByCustomer(String customer);
}