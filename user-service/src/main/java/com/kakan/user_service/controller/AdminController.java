package com.kakan.user_service.controller;

import com.kakan.user_service.dto.response.AccountDto;
import com.kakan.user_service.dto.response.AccountInformationDto;
import com.kakan.user_service.dto.response.ResponseDto;
import com.kakan.user_service.dto.response.UserInformationDto;
import com.kakan.user_service.mapper.AccountMapper;
import com.kakan.user_service.mapper.UserInformationMapper;
import com.kakan.user_service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    UserInformationMapper userInformationMapper;


    @GetMapping("/user-list/test")
    public ResponseDto<List<UserInformationDto>> getAllUserTest() {
        List<UserInformationDto> userInformationDtos = userInformationMapper.toDtoList(adminService.getAllUser());
        return new ResponseDto<>(HttpStatus.OK.value(),"successfully", userInformationDtos);
    }

    @GetMapping("/user-list")
    public ResponseDto<List<AccountInformationDto>> getAllUser() {
        List<AccountInformationDto> userInformationDtos = adminService.getAllUserInformationByAdmin();
        return new ResponseDto<>(HttpStatus.OK.value(),"successfully", userInformationDtos);
    }

    @PutMapping("/block-user/{id}")
    public ResponseDto blockUser(@PathVariable int id) {
        adminService.blockUser(id);
        return new ResponseDto<>(HttpStatus.OK.value(), "Chặn người dùng thành công", null);
    }

    @PutMapping("/active-user/{id}")
    public ResponseDto activeUser(@PathVariable int id) {
        adminService.activeUser(id);
        return new ResponseDto<>(HttpStatus.OK.value(), "Người dùng đã được kích hoạt", null);
    }
}
