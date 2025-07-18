package com.kakan.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {

    private String username;
    @Email(message = "Địa chỉ email không hợp lệ.")
    private String email;
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
            message = "Mật khẩu phải bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.")
    private String password;
    private String fullName;
    private String confirmPassword;
    
    @NotNull(message = "Giới tính không được để trống")
    private Boolean gender;
    
    @NotNull(message = "Ngày sinh không được để trống")
    private LocalDate dob;
}
