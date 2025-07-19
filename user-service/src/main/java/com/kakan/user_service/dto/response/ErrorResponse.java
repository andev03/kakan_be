package com.kakan.user_service.dto.response;
import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private int status;
    private String message;
    private List<String> errors; // Danh sách lỗi chi tiết

    public ErrorResponse(int status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
