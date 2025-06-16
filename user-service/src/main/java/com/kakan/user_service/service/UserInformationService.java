package com.kakan.user_service.service;

import com.kakan.user_service.dto.request.UpdateUserInformationRequest;
import com.kakan.user_service.dto.response.UserInformationDto;

public interface UserInformationService {
    UserInformationDto updateUserInformation(UpdateUserInformationRequest request);
}
