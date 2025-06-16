package com.kakan.user_service.service.impl;

import com.kakan.user_service.dto.request.UpdateUserInformationRequest;
import com.kakan.user_service.dto.response.UserInformationDto;
import com.kakan.user_service.pojo.UserInformation;
import com.kakan.user_service.repository.UserInformationRepository;
import com.kakan.user_service.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInformationServiceImpl implements UserInformationService {

    private final UserInformationRepository userInformationRepository;

    public UserInformationServiceImpl(UserInformationRepository userInformationRepository) {
        this.userInformationRepository = userInformationRepository;
    }

    @Override
    @Transactional
    public UserInformationDto updateUserInformation(UpdateUserInformationRequest request) {
        UserInformation userInformation = userInformationRepository.findByUserId(request.getUserId());
        userInformation.setDob(request.getDob());
        userInformation.setGender(request.getGender());
        userInformation.setPhone(request.getPhone());
        userInformation.setAddress(request.getAddress());
        userInformation.setAvatarUrl(request.getAvatarUrl());
        userInformationRepository.save(userInformation);
        return new UserInformationDto(userInformation.getUserId(),
                userInformation.getGender(),
                userInformation.getDob(),
                userInformation.getPhone(),
                userInformation.getAddress(),
                userInformation.getAvatarUrl());
    }
}
