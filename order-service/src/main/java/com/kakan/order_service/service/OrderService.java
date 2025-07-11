package com.kakan.order_service.service;

import com.kakan.order_service.dto.CustomerOrder;
import com.kakan.order_service.pojo.Order;

public interface OrderService {
    Order createOrder(CustomerOrder customerOrder) ;

}
