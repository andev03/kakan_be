package com.kakan.user_service.controller;

import com.kakan.user_service.dto.request.LoginRequest;
import com.kakan.user_service.dto.request.RegisterRequest;
import com.kakan.user_service.dto.response.AccountDto;
import com.kakan.user_service.dto.response.AccountResponse;
import com.kakan.user_service.dto.response.ResponseDto;
import com.kakan.user_service.exception.DuplicateEntity;
import com.kakan.user_service.mapper.AccountMapper;
import com.kakan.user_service.pojo.Account;
import com.kakan.user_service.service.AccountService;
import com.kakan.user_service.service.impl.AuthenticationServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountController {

    final AccountService accountService;

    final AccountMapper accountMapper;
    final AuthenticationServiceImpl Au;

    public AccountController(AccountService accountService, AccountMapper accountMapper, AuthenticationServiceImpl au) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        Au = au;
    }



    @GetMapping("/test2/{id}")
    public AccountDto getAccountById(@PathVariable int id) {
        return accountMapper.toDto(accountService.getAccountById(id));
    }
//    @GetMapping("/test")
//    public ResponseDto responseDto(){
//        return accountService.responses();
//    }

    @GetMapping("/")
    public String Hello(HttpServletRequest request) {
        return "Hello, this is user-service!" + request.getSession().getId();
    }




}
