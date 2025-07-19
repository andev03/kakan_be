package com.kakan.order_service.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {

    private int status;

    private String message;

    private T data;
}
