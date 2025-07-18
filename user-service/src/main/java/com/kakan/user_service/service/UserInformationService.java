package com.kakan.user_service.service;

import com.kakan.user_service.dto.request.UpdateUserInformationRequest;
import com.kakan.user_service.dto.response.UserInformationDto;
import com.kakan.user_service.dto.response.ViewUserInformationResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserInformationService {
    void updateUserInformation(UpdateUserInformationRequest request) throws IOException;
    void uploadImage(final int id, final MultipartFile file);
    ViewUserInformationResponse viewUserInformation();
}
