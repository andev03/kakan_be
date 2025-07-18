package com.kakan.payment_service.exception;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(Integer paymentId) {
        super("Cannot find payment by payment id: " + paymentId);
    }
}