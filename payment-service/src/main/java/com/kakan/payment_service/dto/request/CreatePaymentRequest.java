package com.kakan.payment_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePaymentRequest {
    @NotNull(message = "Order ID is required")
    private Integer orderId;

    @NotNull(message = "Account ID is required")
    private Integer accountId;
}
