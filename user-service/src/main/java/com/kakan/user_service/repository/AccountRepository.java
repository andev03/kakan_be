package com.kakan.user_service.repository;

import com.kakan.user_service.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
