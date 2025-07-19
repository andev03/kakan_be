package com.kakan.user_service.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class AccountInformationDto {
    private Integer userId;
    private String email;       // từ Account
    private String fullName;
    private Boolean gender;
    private LocalDate dob;
    private String phone;
    private String address;
    private boolean isActive;
    private OffsetDateTime createDate;
    private String role;
    private String avatarUrl;
}
