package com.kakan.user_service.controller;

import com.kakan.user_service.dto.request.OrderStatusRequest;
import com.kakan.user_service.dto.response.ResponseDto;
import com.kakan.user_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/updateRole")
    public ResponseDto updateRole(@RequestBody OrderStatusRequest orderStatusRequest) {
        orderService.updateRole(orderStatusRequest);
        System.out.println("Role updated for account ID: " + orderStatusRequest.getAccountId() + " with status: " + orderStatusRequest.getStatus());
        return new ResponseDto(HttpStatus.OK.value(), "Role updated successfully", null);
    }
}
