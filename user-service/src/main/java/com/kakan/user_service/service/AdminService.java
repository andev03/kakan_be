package com.kakan.user_service.service;

import com.kakan.user_service.dto.response.AccountInformationDto;
import com.kakan.user_service.pojo.Account;
import com.kakan.user_service.pojo.UserInformation;

import java.util.List;

public interface AdminService {
    public void blockUser(int id);
    public void activeUser(int id);

    public List<UserInformation> getAllUser();
    public List<AccountInformationDto> getAllUserInformationByAdmin();




    }
