package com.backendteam5.finalproject.dto;


import com.backendteam5.finalproject.entity.Courier;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SearchResponseDto {
    private List<Courier> data;
    private Long cnt;
}
