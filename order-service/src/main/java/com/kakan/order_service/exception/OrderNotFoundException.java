package com.kakan.order_service.exception;


public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Integer orderId) {
        super("Cannot find post by post id: " + orderId);
    }
}
