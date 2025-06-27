package com.kakan.user_service.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInformationRequest {

    private String fullName;

    private String phone;

    private String address;

    private  MultipartFile avatarUrl;

}
