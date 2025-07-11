package com.kakan.order_service.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerOrder {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer orderId;

    @NotNull(message = "Account ID is required")
    private Integer accountId;

    @NotNull(message = "Amount is required")
    private Double amount;
}
