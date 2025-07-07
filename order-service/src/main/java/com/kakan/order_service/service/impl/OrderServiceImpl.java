package com.kakan.order_service.service.impl;

import com.kakan.order_service.dto.OrderCreatedEvent;
import com.kakan.order_service.pojo.Order;
import com.kakan.order_service.repository.OrderRepository;
import com.kakan.order_service.service.OrderService;
import lombok.SneakyThrows;
import org.apache.kafka.common.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static java.time.OffsetDateTime.now;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired private KafkaTemplate<String, Object> kafkaTemplate;
    @Transactional
    @SneakyThrows
    public Order createOrder(int userId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus("PENDING");
        order.setPrice(50000.00); // Mặc định giá là 50k
        order.setOrderDate(now());
        order.setExpiredDate(now().plusDays(30)); // Hết hạn sau 30 ngày kể từ ngày tạo
        order.setUpdatedAt(now());
        orderRepository.save(order);

        // phát event OrderCreated
        OrderCreatedEvent evt = new OrderCreatedEvent(
                order.getOrderId(),
                userId,
                order.getPrice()
        );
        kafkaTemplate.send("order.created", evt);
        return order;

    }


}
