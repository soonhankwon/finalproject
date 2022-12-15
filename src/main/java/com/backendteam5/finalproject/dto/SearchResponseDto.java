package com.backendteam5.finalproject.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SearchResponseDto {
    private List<CourierDto> data;
    private Long completeCnt;
    private Long progressCnt;
    private Long beforeCnt;
}
