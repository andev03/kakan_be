package com.kakan.user_service.dto.response;

import lombok.Data;

import java.time.LocalDate;
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
    private String avatarUrl;
}
