package com.kakan.payment_service.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePaymentResponse {
    private String message;

    private String paymentUrl;
}
