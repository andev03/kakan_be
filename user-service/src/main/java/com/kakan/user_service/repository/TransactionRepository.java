package com.kakan.user_service.repository;

import com.kakan.user_service.pojo.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
