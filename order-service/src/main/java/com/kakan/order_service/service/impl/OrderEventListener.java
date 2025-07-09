package com.kakan.order_service.service.impl;

import com.kakan.order_service.dto.PaymentFailedEvent;
import com.kakan.order_service.dto.PaymentSucceededEvent;
import com.kakan.order_service.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Slf4j
@Service
public class OrderEventListener {
    private final OrderRepository orderRepository;

    public OrderEventListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        log.info("OrderEventListener initialized successfully");
    }

    @KafkaListener(
            topics = "payment.succeeded",
            containerFactory = "paymentSucceededKafkaListenerFactory"
    )
    public void onPaymentSucceeded(PaymentSucceededEvent event) {
        log.info("OrderEventListener received PaymentSucceededEvent: orderId={}, accountId={}, paymentId={}",
                event.getOrderId(), event.getAccountID(), event.getPaymentId());
        
        try {
            orderRepository.findById(event.getOrderId()).ifPresent(order -> {
                log.info("Updating order {} from status {} to ACTIVE", order.getOrderId(), order.getStatus());
                order.setStatus("ACTIVE");
                order.setUpdatedAt(OffsetDateTime.now());
                orderRepository.save(order);
                log.info("Successfully updated order {} to ACTIVE status", order.getOrderId());
            });
        } catch (Exception e) {
            log.error("Error processing PaymentSucceededEvent for orderId {}: {}", event.getOrderId(), e.getMessage(), e);
            throw e; // Re-throw để Kafka có thể retry nếu cần
        }
    }

    @KafkaListener(
            topics = "payment.failed",
            containerFactory = "paymentFailedKafkaListenerFactory"
    )
    public void onPaymentFailed(PaymentFailedEvent event) {
        log.info("OrderEventListener received PaymentFailedEvent: orderId={}, reason={}",
                event.getOrderId(), event.getReason());
        
        try {
            orderRepository.findById(event.getOrderId()).ifPresent(order -> {
                log.info("Updating order {} from status {} to CANCELLED", order.getOrderId(), order.getStatus());
                order.setStatus("CANCELLED");
                order.setUpdatedAt(OffsetDateTime.now());
                orderRepository.save(order);
                log.info("Successfully updated order {} to CANCELLED status", order.getOrderId());
            });
        } catch (Exception e) {
            log.error("Error processing PaymentFailedEvent for orderId {}: {}", event.getOrderId(), e.getMessage(), e);
            throw e; // Re-throw để Kafka có thể retry nếu cần
        }
    }
}
