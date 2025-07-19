package com.kakan.user_service.dto.request;

import lombok.*;
import org.mapstruct.Mapping;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusRequest {
    private int accountId;
    private String status;
}
