package com.kakan.order_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderResponseDto {
    private Integer orderId;

    private Integer accountId;

    private String status;
}
