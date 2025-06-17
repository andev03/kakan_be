package com.kakan.user_service.service;

import com.kakan.user_service.dto.request.UpdateUserInformationRequest;
import com.kakan.user_service.dto.response.UserInformationDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserInformationService {
    UpdateUserInformationRequest updateUserInformation(UpdateUserInformationRequest request);
    void uploadImage(final int id, final MultipartFile file);

}
