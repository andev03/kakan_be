package com.kakan.order_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakan.order_service.dto.OrderCreatedEvent;
import com.kakan.order_service.pojo.Order;
import com.kakan.order_service.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class OrderEventListener {
    private final OrderRepository orderRepository;

    public OrderEventListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "reversed-orders", groupId = "orders-group")
    public void reverseOrder(String event) {
        System.out.println("Inside reverse order for order "+event);

        try {
            OrderCreatedEvent orderEvent = new ObjectMapper().readValue(event, OrderCreatedEvent.class);

            Optional<Order> order = orderRepository.findById(orderEvent.getOrder().getOrderId());

            order.ifPresent(o -> {
                o.setStatus("FAILED");
                this.orderRepository.save(o);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "new-payments", groupId = "orders-group")
    public void succeedPayment(String event){
        try{
            OrderCreatedEvent orderEvent = new ObjectMapper().readValue(event, OrderCreatedEvent.class);
            Optional<Order> order = orderRepository.findById(orderEvent.getOrder().getOrderId());
            order.ifPresent(o -> {
                o.setStatus("ACTIVE");
                this.orderRepository.save(o);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
