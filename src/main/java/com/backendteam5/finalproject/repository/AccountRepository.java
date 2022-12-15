package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.repository.custom.CustomAccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, CustomAccountRepository {
    Optional<Account> findByUsername(String username);

    Boolean existsByUsername(String username);
}
