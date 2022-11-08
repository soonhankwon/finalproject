package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    List<Courier> findByRoute(String route);
    List<Courier> findByRouteAndSubRoute(String route, Long subRoute);
}
