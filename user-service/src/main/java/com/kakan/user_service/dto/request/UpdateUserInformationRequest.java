package com.kakan.user_service.dto.request;

import lombok.Data;

import java.time.LocalDate;
@Data
public class UpdateUserInformationRequest {

    private Integer userId;

    private String fullName;

    private Boolean gender;

    private LocalDate dob;

    private String phone;

    private String address;

    private String avatarUrl;

}
