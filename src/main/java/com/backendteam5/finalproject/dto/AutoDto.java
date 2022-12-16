package com.backendteam5.finalproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AutoDto {
    private List<String> userList;
    private List<Integer> capacity;
    private List<Long> subRouteCount;
    private List<Double> difficulty;

    public AutoDto(List<String> userList, List<Integer> capacity,
                   List<Long> subRouteCount, List<Double> difficulty){
        this.userList = userList;
        this.capacity = capacity;
        this.subRouteCount = subRouteCount;
        this.difficulty = difficulty;
    }
}
