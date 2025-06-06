package com.kakan.user_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class AccountDto {
    private Integer id;

    private String email;

    private String password;

    private String fullName;

    private Boolean isActive;

    private OffsetDateTime createDate;
}
