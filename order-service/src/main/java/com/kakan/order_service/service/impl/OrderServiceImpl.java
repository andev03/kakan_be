package com.kakan.order_service.service.impl;

import com.kakan.order_service.dto.OrderCreatedEvent;
import com.kakan.order_service.pojo.Order;
import com.kakan.order_service.repository.OrderRepository;
import com.kakan.order_service.service.OrderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;

import static java.time.OffsetDateTime.now;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired 
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Transactional
    @SneakyThrows
    public Order createOrder(int accountId) {
        Order order = new Order();
        order.setAccountId(accountId);
        order.setStatus("PENDING");
        order.setPrice(50000.00); // Mặc định giá là 50k
        order.setOrderDate(now());
        order.setExpiredDate(now().plusDays(30)); // Hết hạn sau 30 ngày kể từ ngày tạo
        order.setUpdatedAt(now());
        orderRepository.save(order);

        // phát event OrderCreated
        OrderCreatedEvent evt = new OrderCreatedEvent(
                order.getOrderId(),
                accountId,
                order.getPrice()
        );
        
        log.info("Sending OrderCreatedEvent for orderId: {}, accountId: {}, amount: {}", 
                order.getOrderId(), accountId, order.getPrice());
        
        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("order.created", evt);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Successfully sent OrderCreatedEvent for orderId: {} with offset: {}",
                            order.getOrderId(), result.getRecordMetadata().offset());
                } else {
                    log.error("Failed to send OrderCreatedEvent for orderId: {}: {}", 
                            order.getOrderId(), ex.getMessage(), ex);
                }
            });
        } catch (Exception e) {
            log.error("Error sending OrderCreatedEvent for orderId: {}: {}", order.getOrderId(), e.getMessage(), e);
            throw e;
        }
        
        return order;

    }


}
