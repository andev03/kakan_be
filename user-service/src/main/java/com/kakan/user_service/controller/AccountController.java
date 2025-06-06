package com.kakan.user_service.controller;

import com.kakan.user_service.dto.response.ResponseDto;
import com.kakan.user_service.service.AccountService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountController {

    final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/test")
    public ResponseDto responseDto(){
        return accountService.responses();
    }
}
