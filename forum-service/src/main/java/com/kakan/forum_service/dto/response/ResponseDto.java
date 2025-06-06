package com.kakan.forum_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseDto<T> {

    private int status;

    private String message;

    private T data;
}
