package com.kakan.order_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakan.order_service.dto.OrderCreatedEvent;
import com.kakan.order_service.dto.OrderResponseDto;
import com.kakan.order_service.dto.PaymentEvent;
import com.kakan.order_service.dto.request.OrderDto;
import com.kakan.order_service.dto.request.OrderRequestDto;
import com.kakan.order_service.exception.OrderNotFoundException;
import com.kakan.order_service.pojo.Order;
import com.kakan.order_service.repository.OrderRepository;
import com.kakan.order_service.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.time.OffsetDateTime.now;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) throws JsonProcessingException {
        Order savedOrder = saveOrder(orderRequestDto);

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();

        orderCreatedEvent.setOrderId(savedOrder.getOrderId());
        orderCreatedEvent.setAccountId(savedOrder.getAccountId());
        orderCreatedEvent.setAmount(savedOrder.getPrice());
        orderCreatedEvent.setStatus(savedOrder.getStatus());

        String jsonString = objectMapper.writeValueAsString(orderCreatedEvent);

        kafkaTemplate.send("order.success", jsonString);

        return OrderResponseDto.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .accountId(orderCreatedEvent.getAccountId())
                .status(orderCreatedEvent.getStatus())
                .build();
    }

    @Override
    public OrderDto getOrderByAccountId(int accountId) {
        Optional<Order> order = orderRepository.findByAccountId(accountId);
        OrderDto orderDto = new OrderDto();
        if (order.isPresent()) {
            Order foundOrder = order.get();
            orderDto.setOrderId(foundOrder.getOrderId());
            orderDto.setOrderDate(foundOrder.getOrderDate());
            orderDto.setExpiredDate(foundOrder.getExpiredDate());
        } else {
            throw new EntityNotFoundException("Order not found for account ID: " + accountId);
        }
        return orderDto;
    }

    private Order saveOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setAccountId(orderRequestDto.getAccountId());
        order.setPrice(orderRequestDto.getAmount());
        order.setStatus("PENDING");
        order.setUpdatedAt(now());
        order.setExpiredDate(now().plusDays(30)); // Assuming orders expire in 1 day
//        order.setExpiredDate(now().plusMinutes(2));
        return orderRepository.save(order);
    }

    @KafkaListener(topics = "payment.success", groupId = "payments-kakan-group")
    public void handlePaymentEvent(String orderString) throws JsonProcessingException {

        PaymentEvent payment = objectMapper.readValue(orderString, PaymentEvent.class);

        Order order = orderRepository.findById(payment.getOrderId()).orElseThrow(() -> new OrderNotFoundException(payment.getOrderId()));

        order.setStatus(payment.getPaymentStatus());
        order.setUpdatedAt(now());

        orderRepository.save(order);
    }

    @Scheduled(fixedRate = 60_000)  // hàng ngày lúc 00:00
    public void checkExpiredOrders() {
        orderRepository.findAll().forEach(order -> {
            if (order.getExpiredDate().isBefore(now()) && order.getStatus().equals("SUCCESS")) {
                order.setStatus("EXPIRED");
                order.setUpdatedAt(now());
                orderRepository.save(order);
            }
        });
    }
}
