package com.kakan.user_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerOrder {
    @NotNull(message = "Order ID is required")
    private Integer orderId;

    @NotNull(message = "Account ID is required")
    private Integer accountId;

    @NotNull(message = "Amount is required")
    private Double amount;
}
