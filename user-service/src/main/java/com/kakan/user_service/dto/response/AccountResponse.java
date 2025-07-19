package com.kakan.user_service.dto.response;

import lombok.Data;

@Data
public class AccountResponse {
    int id;
    String email;
    String userName;
    String role;
    String token;
    boolean isActive;
}
