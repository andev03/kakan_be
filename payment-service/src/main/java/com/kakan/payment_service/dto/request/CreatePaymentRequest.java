package com.kakan.payment_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
    @NotNull(message = "Order ID is required")
    private Integer orderId;

    @NotNull(message = "Account ID is required")
    private Integer accountId;

    @NotNull(message = "Amount is required")
    private Double amount;
}
