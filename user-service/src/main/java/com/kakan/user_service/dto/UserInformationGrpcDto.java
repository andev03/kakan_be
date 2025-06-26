package com.kakan.user_service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class UserInformationGrpcDto {
    private Integer userId;

    private String fullName;

    private Boolean gender;

    private LocalDate dob;

    private String phone;

    private String address;

    private String avatarUrl;
}
