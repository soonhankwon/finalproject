package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.entity.CourierInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourierInfoRepository extends JpaRepository<CourierInfo, Long> {
    List<CourierInfo> findByLocationStartingWith(String location);
}
