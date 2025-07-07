package com.kakan.order_service.repository;

import com.kakan.order_service.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    // Additional query methods can be defined here if needed
}
