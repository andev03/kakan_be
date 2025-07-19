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

    public UserInformationDto(Integer userId, Boolean gender, LocalDate dob, String phone, String address, String avatarUrl) {
        this.userId = userId;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.avatarUrl = avatarUrl;
    }
}
