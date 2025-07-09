package com.kakan.order_service.controller;

import com.kakan.order_service.dto.response.ResponseDto;
import com.kakan.order_service.pojo.Order;
import com.kakan.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;
    @PostMapping("/create/{accountId}")
    public ResponseDto<Order> createOrder(@PathVariable int accountId) {
        try{
           Order order = orderService.createOrder(accountId);
           return new ResponseDto<>(200, "Order created successfully", order);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
