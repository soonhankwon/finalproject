package com.backendteam5.finalproject.repository.custom;

public interface CustomAccountRepository{
    Long findIdByUsername(String username);
}
