package com.kakan.user_service.service.impl;

import com.kakan.user_service.dto.request.UpdateUserInformationRequest;
import com.kakan.user_service.dto.response.CloudinaryResponse;
import com.kakan.user_service.dto.response.ViewUserInformationResponse;
import com.kakan.user_service.exception.DuplicateEntity;
import com.kakan.user_service.exception.NotFoundException;
import com.kakan.user_service.pojo.Account;
import com.kakan.user_service.pojo.UserInformation;
import com.kakan.user_service.repository.AccountRepository;
import com.kakan.user_service.repository.UserInformationRepository;
import com.kakan.user_service.service.UserInformationService;
import com.kakan.user_service.util.FileUpLoadUtil;
import lombok.Builder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;


@Service
public class UserInformationServiceImpl implements UserInformationService {

    @Autowired
    CloudinaryService cloudinaryService;
    private final UserInformationRepository userInformationRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    public UserInformationServiceImpl(UserInformationRepository userInformationRepository, ModelMapper modelMapper, AccountRepository accountRepository) {
        this.userInformationRepository = userInformationRepository;
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
    }





    @Override
    @Transactional
    public UpdateUserInformationRequest updateUserInformation(UpdateUserInformationRequest request) {
        try{
            Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserInformation userInformation = userInformationRepository.findByAccountId(account.getId());
            if (request.getPhone() != null) {

                if (!request.getPhone().equals(userInformation.getPhone()) && userInformationRepository.existsByPhone(request.getPhone())) {
                    throw new DuplicateEntity("Số điện thoại đã được sử dụng.");
                }
                // Nếu số điện thoại mới khác số điện thoại cũ (và không trùng lặp)
                if (!request.getPhone().equals(userInformation.getPhone())) {
                    userInformation.setPhone(request.getPhone());
                }
            }

            if (request.getFullName() != null && !Objects.equals(request.getFullName(), userInformation.getFullName())) {
                userInformation.setFullName(request.getFullName());
            }
            if (request.getAddress() != null && !Objects.equals(request.getAddress(), userInformation.getAddress())) {
                userInformation.setAddress(request.getAddress());
            }
            // Xử lý upload ảnh nếu có
            if (request.getAvatarUrl() != null && !request.getAvatarUrl().isEmpty()) {
                uploadImage(account.getId(), request.getAvatarUrl());
            }
            userInformationRepository.save(userInformation);
            return modelMapper.map(userInformation, UpdateUserInformationRequest.class);
        }catch (DuplicateEntity e) {
            throw new DuplicateEntity(e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("Đã xảy ra lỗi trong quá trình cập nhật thông tin học sinh.");
        }

    }

    @Transactional
    public void uploadImage(int id, final MultipartFile file) {
        final UserInformation userInformation = userInformationRepository.findByAccountId(id);
        if (userInformation == null) {
            throw new NotFoundException("Người dùng không tồn tại.");
        }
        FileUpLoadUtil.assertAllowed(file, FileUpLoadUtil.IMAGE_PATTERN);
        final String fileName = FileUpLoadUtil.getFileName(file.getOriginalFilename());
        final CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(file, fileName);
        userInformation.setAvatarUrl(cloudinaryResponse.getUrl());
        userInformationRepository.save(userInformation);
    }

    @Override
    public ViewUserInformationResponse viewUserInformation() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInformation userInformation = userInformationRepository.findByAccountId(account.getId());
        return ViewUserInformationResponse.builder()
                .userId(userInformation.getUserId())
                .fullName(userInformation.getFullName())
                .gender(userInformation.getGender())
                .dob(userInformation.getDob())
                .phone(userInformation.getPhone())
                .address(userInformation.getAddress())
                .avatarUrl(userInformation.getAvatarUrl())
                .gpa(userInformation.getGpa())
                .build();
    }


}
