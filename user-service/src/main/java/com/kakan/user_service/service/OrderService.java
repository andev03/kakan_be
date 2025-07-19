package com.kakan.user_service.service;

import com.kakan.user_service.dto.request.OrderStatusRequest;
import com.kakan.user_service.dto.response.OrderDto;
import com.kakan.user_service.pojo.Order;

public interface OrderService {
    void updateRole(OrderStatusRequest orderStatusRequest) ;
     void updateRoleStudent(OrderStatusRequest orderStatusRequest);

}
