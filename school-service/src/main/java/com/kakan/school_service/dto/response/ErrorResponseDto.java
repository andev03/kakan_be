package com.kakan.school_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private  String apiPath;
    private HttpStatus errorCode;
    private  String errorMessage;
    private LocalDateTime errorTime;

}
