package com.kakan.user_service.controller;

import com.kakan.user_service.dto.response.AccountDto;
import com.kakan.user_service.dto.response.ResponseDto;
import com.kakan.user_service.mapper.AccountMapper;
import com.kakan.user_service.service.AccountService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountController {

    final AccountService accountService;

    final AccountMapper accountMapper;

    public AccountController(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @GetMapping("/test")
    public List<AccountDto> responseDto(){
        return accountMapper.toDtoList(accountService.getAllAccounts());
    }

    @GetMapping("/test2/{id}")
    public AccountDto getAccountById(@PathVariable int id){
        return accountMapper.toDto(accountService.getAccountById(id));
    }
//    @GetMapping("/test")
//    public ResponseDto responseDto(){
//        return accountService.responses();
//    }
}
