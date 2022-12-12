package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.entity.DeliveryAssignment;
import com.backendteam5.finalproject.repository.custom.CustomCourierRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CourierRepository extends JpaRepository<Courier, Long>, CustomCourierRepository {

    List<Courier> findByStateAndDeliveryAssignmentAndRegisterDateOrderByRegisterDate(String state , DeliveryAssignment deliveryAssignment, String registerDate);

}
