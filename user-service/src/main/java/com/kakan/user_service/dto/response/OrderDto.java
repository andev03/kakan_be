package com.kakan.user_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class OrderDto {

    private Integer orderId;

    private String packageName;

    private Integer packageDuration;

    private Double price;

    private OffsetDateTime orderDate;

    private OffsetDateTime expiredDate;

    private String note;
}
