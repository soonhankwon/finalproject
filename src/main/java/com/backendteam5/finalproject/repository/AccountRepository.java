package com.backendteam5.finalproject.repository;

import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>  {
    Optional<Account> findByUsername(String username);
    List<Account> findByUsernameStartingWith(String username);
    List<Account> findByRouteAndRole(String route, UserRoleEnum role);

}
