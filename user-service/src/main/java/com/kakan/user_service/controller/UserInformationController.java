package com.kakan.user_service.controller;

import com.kakan.user_service.dto.request.UpdateUserInformationRequest;
import com.kakan.user_service.dto.response.ResponseDto;
import com.kakan.user_service.dto.response.UserInformationDto;
import com.kakan.user_service.dto.response.ViewUserInformationResponse;
import com.kakan.user_service.exception.DuplicateEntity;
import com.kakan.user_service.service.UserInformationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInformationController {

    private final UserInformationService userInformationService;


    public UserInformationController(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    @PutMapping("/user/information")
    public ResponseDto<UpdateUserInformationRequest> updateUserInformation(@RequestBody UpdateUserInformationRequest request) {
        try{
            UpdateUserInformationRequest result = userInformationService.updateUserInformation(request);
            return new ResponseDto<>(HttpStatus.OK.value(), "Cập nhật thông tin học sinh thành công.", result );
        }catch (DuplicateEntity e){
            return new ResponseDto<>(HttpStatus.CONFLICT.value(), e.getMessage(), null);
        } catch (RuntimeException e) {
            return  new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Đã xảy ra lỗi trong quá trình cập nhật thông tin học sinh.", null);
        }

    }

    @PostMapping("/uploadImage/{id}")
    public ResponseEntity uploadImage(@PathVariable int id,@RequestPart MultipartFile file) {
        userInformationService.uploadImage(id,file);
        return ResponseEntity.ok("Uploaded image successfully");
    }

    @GetMapping("/user/information/")
    public ResponseDto<ViewUserInformationResponse> getUserInformation() {
        ViewUserInformationResponse userInformation = userInformationService.viewUserInformation();
        return new ResponseDto<ViewUserInformationResponse>(HttpStatus.OK.value(), "Lấy thông tin người dùng thành công.", userInformation);
    }

}