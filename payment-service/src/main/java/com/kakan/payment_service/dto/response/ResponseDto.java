package com.kakan.payment_service.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    private int status;

    private String message;

    private T data;
}
