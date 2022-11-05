package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}
