package com.kakan.user_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInformationRequest {

    private String fullName;

    @Pattern(regexp = "^(032|033|034|035|036|037|038|039|086|096|097|098|"
            + "070|076|077|078|079|089|090|093|"
            + "081|082|083|084|085|088|091|094|"
            + "056|058|092|087|099)\\d{7}$",
            message = "Invalid phone number format. Must be a valid Vietnamese phone number.")
    private String phone;

    private String address;

    private String gender;
    private LocalDate dob;

    private MultipartFile avatarUrl;

}