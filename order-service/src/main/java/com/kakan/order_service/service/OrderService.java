package com.kakan.order_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kakan.order_service.dto.OrderResponseDto;
import com.kakan.order_service.dto.request.OrderRequestDto;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto) throws JsonProcessingException;
}
