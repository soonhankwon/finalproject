package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.entity.DeliveryAssignment;
import com.backendteam5.finalproject.repository.custom.CustomDeliveryAssignmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAssignmentRepository extends JpaRepository<DeliveryAssignment, Long>, CustomDeliveryAssignmentRepository {

}
