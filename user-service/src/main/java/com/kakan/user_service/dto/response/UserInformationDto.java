package com.kakan.user_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserInformationDto {
    private Integer userId;

    private Boolean gender;

    private LocalDate dob;

    private String phone;

    private String address;

    private String avatarUrl;
}
