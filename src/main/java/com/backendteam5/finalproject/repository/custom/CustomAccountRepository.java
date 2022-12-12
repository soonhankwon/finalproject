package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.entity.UserRoleEnum;

import java.util.List;

public interface CustomAccountRepository{
    Long findIdByUsername(String username);

    List<String> findByAreaAndRole(String area, UserRoleEnum role);
}
