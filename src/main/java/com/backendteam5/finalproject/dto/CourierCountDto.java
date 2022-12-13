package com.backendteam5.finalproject.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class CourierCountDto {

    private Long progressCnt;
    private Long completeCnt;


    public CourierCountDto(Long progressCnt, Long completeCnt) {
        this.progressCnt = progressCnt;
        this.completeCnt = completeCnt;
    }
}
