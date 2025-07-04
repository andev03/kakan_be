package com.kakan.user_service.service;

import com.kakan.user_service.dto.request.LoginRequest;
import com.kakan.user_service.dto.request.RegisterRequest;
import com.kakan.user_service.dto.response.AccountResponse;
import com.kakan.user_service.pojo.Account;

import java.util.List;

public interface AccountService {

    Account getAccountById(int id);
   AccountResponse login(LoginRequest loginRequest);




}
