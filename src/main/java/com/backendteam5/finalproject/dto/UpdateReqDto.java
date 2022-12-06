package com.backendteam5.finalproject.dto;

import com.backendteam5.finalproject.entity.DeliveryAssignment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UpdateReqDto {
    private List<String> deliveryPerson;
    private List<DeliveryAssignment> deliveryAssignments;
    private List<Long> courierIds;
    private List<String> routes;
    private List<Integer> subRoutes;
}
