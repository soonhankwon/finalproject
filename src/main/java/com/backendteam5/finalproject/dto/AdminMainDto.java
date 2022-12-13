package com.backendteam5.finalproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdminMainDto {
    private List<String> userlist;
    private List<CountUserDto> tempAssignment;
    private List<CountUserDto> directAssignment;

    public AdminMainDto(List<String> userlist,
                        List<CountUserDto> temp,
                        List<CountUserDto> direct){
        this.userlist = userlist;
        this.tempAssignment = temp;
        this.directAssignment = direct;
    }
}
