package com.kakan.user_service.dto.response;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewUserInformationResponse {
    private Integer userId;

    private String fullName;

    private Boolean gender;

    private LocalDate dob;

    private String phone;

    private String address;

    private String avatarUrl;

    private Double gpa;

    private String email;
}
