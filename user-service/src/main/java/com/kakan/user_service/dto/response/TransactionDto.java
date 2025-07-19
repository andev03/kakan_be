package com.kakan.user_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class TransactionDto {

    private Integer transactionId;

    private Double amount;

    private String transactionMethod;

    private OffsetDateTime transactionDate;

    private String responseMessage;
}
