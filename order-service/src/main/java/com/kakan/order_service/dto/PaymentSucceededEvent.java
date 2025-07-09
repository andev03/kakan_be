package com.kakan.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSucceededEvent {
    private Integer orderId;
    private Integer accountID;
    private Integer paymentId;
    // constructors, getters/setters
}