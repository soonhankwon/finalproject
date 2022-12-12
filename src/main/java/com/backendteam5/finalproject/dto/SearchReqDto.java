package com.backendteam5.finalproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class SearchReqDto {
    private String username;
    private String route;
    private List<Integer> subRoute;
    private String state;
    private int currentDay;

    private Boolean option;

    public String getDate(){
        Calendar cal = getNow();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.DATE, this.currentDay*-1);
        return formatter.format(cal.getTime());
    }

    public static Calendar getNow(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal;
    }
}
