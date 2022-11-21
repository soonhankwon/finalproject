package com.backendteam5.finalproject.repository.custom;

import com.backendteam5.finalproject.entity.Courier;

import java.util.List;

public interface CourierRepositoryCustom {

    List<Courier> searchByUsernameAndState(String username, Boolean state);
}
