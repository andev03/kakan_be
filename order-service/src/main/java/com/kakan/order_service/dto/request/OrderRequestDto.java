package com.kakan.order_service.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    @NotNull(message = "Account ID is required")
    private Integer accountId;

    @NotNull(message = "Amount is required")
    private Double amount;
}
