package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.entity.AreaIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaIndexRepository extends JpaRepository<AreaIndex, Long> {
    List<AreaIndex> findByAreaStartingWith(String location);
}