package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.dto.UserDto;
import com.backendteam5.finalproject.entity.UserRoleEnum;

import java.util.List;

public interface CustomAccountRepository{
    Long findIdByUsername(String username);

    List<UserDto> findByAreaAndRole(String area, UserRoleEnum role);
}
