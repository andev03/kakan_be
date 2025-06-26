package com.kakan.forum_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserInformationDto {
    private Integer userId;

    private String fullName;

    private Boolean gender;

    private LocalDate dob;

    private String phone;

    private String address;

    private String avatarUrl;
}
