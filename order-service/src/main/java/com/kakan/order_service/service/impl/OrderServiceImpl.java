package com.kakan.order_service.service.impl;

import com.kakan.order_service.dto.CustomerOrder;
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
    public Order createOrder(CustomerOrder customerOrder) {
        Order order = new Order();
        try{
            order.setAccountId(customerOrder.getAccountId());
            order.setStatus("PENDING");
            order.setPrice(50000.00); // Mặc định giá là 50k
            order.setOrderDate(now());
            order.setExpiredDate(now().plusDays(30)); // Hết hạn sau 30 ngày kể từ ngày tạo
            order.setUpdatedAt(now());
            orderRepository.save(order);

            customerOrder.setOrderId(order.getOrderId());
            // phát event OrderCreated
            OrderCreatedEvent evt = new OrderCreatedEvent();
            evt.setOrder(customerOrder);
            kafkaTemplate.send("new-orders", evt);
            System.out.println(evt);
        } catch (Exception e) {
            order.setStatus("FAILED");
            orderRepository.save(order);
        }
        return order;
    }


}
