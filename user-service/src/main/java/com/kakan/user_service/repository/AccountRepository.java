package com.kakan.user_service.repository;

import com.kakan.user_service.pojo.Account;
import com.kakan.user_service.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findAccountById(int id);
    Account findAccountByUserName(String userName);
    Account findAccountByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    boolean existsById(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.isActive = :isActive WHERE a.id = :id")
    void deactivateAccount(@Param("isActive") boolean isActive,@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.role = :role WHERE a.id = :id")
    int updateRoleById(@Param("id") Integer id, @Param("role") String role);
}
