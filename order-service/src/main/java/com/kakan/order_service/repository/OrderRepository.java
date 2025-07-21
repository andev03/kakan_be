package com.kakan.order_service.repository;

import com.kakan.order_service.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByAccountIdAndStatus(int accountId, String status);
    Order findOrderByStatus(String status);

}
