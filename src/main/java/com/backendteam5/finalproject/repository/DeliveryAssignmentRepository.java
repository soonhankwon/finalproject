package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.DeliveryAssignment;
import com.backendteam5.finalproject.repository.custom.CustomCourierRepository;
import com.backendteam5.finalproject.repository.custom.CustomDeliveryAssignmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryAssignmentRepository extends JpaRepository<DeliveryAssignment, Long>, CustomDeliveryAssignmentRepository {
    List<DeliveryAssignment> findByAccount(Account account);
}
