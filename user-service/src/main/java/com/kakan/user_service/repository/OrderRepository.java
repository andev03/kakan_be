package com.kakan.user_service.repository;

import com.kakan.user_service.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByOrderId(int orderId);
}
