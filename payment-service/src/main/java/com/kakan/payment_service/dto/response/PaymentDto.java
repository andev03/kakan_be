package com.kakan.payment_service.dto.response;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {
    private Integer paymentId;

    private Integer orderId;

    private Integer accountId;

    private Double amount;

    private String status;

    private String responseMessage;
}
