package com.kakan.order_service.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class OrderDto {

    private Integer orderId;

    private OffsetDateTime orderDate;

    private OffsetDateTime expiredDate;

}
