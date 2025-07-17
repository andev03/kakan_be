package com.kakan.user_service.dto.request;

import lombok.Data;

@Data
public class OrderStatusRequest {
    private int accountId;
    private String status;
}
