package com.kakan.order_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakan.order_service.dto.OrderCreatedEvent;
import com.kakan.order_service.dto.OrderResponseDto;
import com.kakan.order_service.dto.request.OrderRequestDto;
import com.kakan.order_service.pojo.Order;
import com.kakan.order_service.repository.OrderRepository;
import com.kakan.order_service.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) throws JsonProcessingException {
        Order savedOrder = saveOrder(orderRequestDto);

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setOrderId(savedOrder.getOrderId());
        orderCreatedEvent.setAccountId(savedOrder.getAccountId());
        orderCreatedEvent.setAmount(savedOrder.getPrice());
        orderCreatedEvent.setStatus(savedOrder.getStatus());

        kafkaTemplate.send("order.success", orderCreatedEvent);

        return OrderResponseDto.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .accountId(orderCreatedEvent.getAccountId())
                .status(orderCreatedEvent.getStatus())
                .build();
    }

    private Order saveOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setAccountId(orderRequestDto.getAccountId());
        order.setPrice(orderRequestDto.getAmount());
        order.setStatus("PENDING");
        return orderRepository.save(order);
    }
}
