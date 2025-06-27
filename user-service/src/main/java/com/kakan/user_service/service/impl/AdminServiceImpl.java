package com.kakan.user_service.service.impl;


import com.kakan.user_service.dto.response.AccountInformationDto;
import com.kakan.user_service.exception.EntityNotFoundException;
import com.kakan.user_service.pojo.Account;
import com.kakan.user_service.pojo.UserInformation;
import com.kakan.user_service.repository.AccountRepository;
import com.kakan.user_service.repository.UserInformationRepository;
import com.kakan.user_service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserInformationRepository userInformationRepository;

    public List<UserInformation> getAllUser(){
        List<UserInformation> users = userInformationRepository.findAll();
        return users;
    }

    public List<AccountInformationDto> getAllUserInformationByAdmin(){
        List<Account> accounts = accountRepository.findAll();
        List<UserInformation> userInformations = userInformationRepository.findAll();
        List<AccountInformationDto> a = new ArrayList<>();
        for (Account account : accounts) {
            for (UserInformation userInformation : userInformations) {
                if (account.getId().equals(userInformation.getAccount().getId())) {
                    AccountInformationDto dto = new AccountInformationDto();
                    dto.setUserId(account.getId());
                    dto.setEmail(account.getEmail());
                    dto.setFullName(userInformation.getFullName());
                    dto.setGender(userInformation.getGender());
                    dto.setDob(userInformation.getDob());
                    dto.setPhone(userInformation.getPhone());
                    dto.setAddress(userInformation.getAddress());
                    dto.setAvatarUrl(userInformation.getAvatarUrl());
                    a.add(dto);
                }
            }
        }
        return a;
    }

    public void blockUser(int id){
        if(accountRepository.existsById(id)){
            accountRepository.deactivateAccount(false ,id);
        }
        else{
            throw new EntityNotFoundException("Đã xảy ra lỗi khi khóa tài khoản");
        }
    }

    public void activeUser(int id){
        if(accountRepository.existsById(id)){
            accountRepository.deactivateAccount(true ,id);
        }
        else{
            throw new EntityNotFoundException("Đã xảy ra lỗi khi kích hoat tài khoản");
        }
    }
}
