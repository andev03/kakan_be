package com.kakan.user_service.repository;

import com.kakan.user_service.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findAccountById(int id);
    Account findAccountByUserName(String userName);
    Account findAccountByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);

}
