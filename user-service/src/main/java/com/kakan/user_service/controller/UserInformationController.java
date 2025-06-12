package com.kakan.user_service.controller;

import com.kakan.user_service.dto.request.UpdateUserInformationRequest;
import com.kakan.user_service.dto.response.ResponseDto;
import com.kakan.user_service.dto.response.UserInformationDto;
import com.kakan.user_service.service.UserInformationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInformationController {

    private final UserInformationService userInformationService;


    public UserInformationController(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    @PutMapping("/user/information")
    public ResponseDto<UserInformationDto> updateUserInformation(@Valid @RequestBody UpdateUserInformationRequest request) {
        UserInformationDto result = userInformationService.updateUserInformation(request);
        return ResponseDto.<UserInformationDto>builder()
                .status(HttpStatus.OK.value())
                .message("User information updated successfully")
                .data(result)
                .build();
    }

}
