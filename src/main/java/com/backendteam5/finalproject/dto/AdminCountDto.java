package com.backendteam5.finalproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdminCountDto {
    private List<UserDto> userlist;
    private List<CountUserDto> tempAssignment;
    private List<CountUserDto> directAssignment;

    public AdminCountDto(List<UserDto> userlist,
                        List<CountUserDto> temp,
                        List<CountUserDto> direct){
        this.userlist = userlist;
        this.tempAssignment = temp;
        this.directAssignment = direct;
    }
}
