package com.kakan.user_service.service.impl;

import com.kakan.user_service.pojo.Account;
import com.kakan.user_service.repository.AccountRepository;
import com.kakan.user_service.service.AccountService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountServiceImpl implements AccountService {
    final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    public Account getAccountById(int id) {
        return accountRepository.findAccountById(id);
    }


}
