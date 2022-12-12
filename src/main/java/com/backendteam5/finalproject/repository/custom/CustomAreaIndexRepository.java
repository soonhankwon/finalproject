package com.backendteam5.finalproject.repository.custom;

import java.util.List;

public interface CustomAreaIndexRepository {
    List<Long> findIdByzipCode(List<String> zipcode);
}
