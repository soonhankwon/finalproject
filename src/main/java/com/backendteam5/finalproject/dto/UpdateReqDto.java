package com.backendteam5.finalproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateReqDto {
    private List<String> usernames;
    private List<Long> courierIds;
    private List<String> routes;
    private List<Integer> subRoutes;
}
