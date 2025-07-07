package com.kakan.order_service.service.impl;

import com.kakan.order_service.dto.PaymentFailedEvent;
import com.kakan.order_service.dto.PaymentSucceededEvent;
import com.kakan.order_service.repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class OrderEventListener {
    private final OrderRepository orderRepository;

    public OrderEventListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaListener(
            topics = "payment.succeeded",
            containerFactory = "paymentSucceededKafkaListenerFactory"
    )
    public void onPaymentSucceeded(PaymentSucceededEvent event) {
        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            order.setStatus("ACTIVE");
            order.setUpdatedAt(OffsetDateTime.now());
            orderRepository.save(order);
        });
    }

    @KafkaListener(
            topics = "payment.failed",
            containerFactory = "paymentFailedKafkaListenerFactory"
    )
    public void onPaymentFailed(PaymentFailedEvent event) {
        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            order.setStatus("CANCELLED");
            order.setUpdatedAt(OffsetDateTime.now());
            orderRepository.save(order);
        });
    }
}
