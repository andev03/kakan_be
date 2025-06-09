package com.kakan.user_service.service;

import com.kakan.user_service.pojo.Account;

import java.util.List;

public interface AccountService {

    List<Account> getAllAccounts();
    Account getAccountById(int id);
}
