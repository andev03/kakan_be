package com.kakan.order_service.controller;

import com.kakan.order_service.dto.OrderResponseDto;
import com.kakan.order_service.dto.request.OrderDto;
import com.kakan.order_service.dto.request.OrderRequestDto;
import com.kakan.order_service.dto.response.ResponseDto;
import com.kakan.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;
    @PostMapping("/create")
    public ResponseDto<Object> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        try{
           OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto);
           return ResponseDto.builder()
                   .message("Order created successfully")
                   .status(200)
                   .data(orderResponseDto)
                   .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{accountId}")
    public ResponseDto getOrderByAccountId(@PathVariable int accountId) {
        OrderDto orderDto = orderService.getOrderByAccountId(accountId);
        return new ResponseDto(HttpStatus.OK.value(), "Order retrieved successfully",orderDto);
    }
}
