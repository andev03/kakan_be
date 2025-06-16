package com.kakan.user_service.dto.request;

import java.time.LocalDate;

public class UpdateUserInformationRequest {

    private Integer userId;

    private Boolean gender;

    private LocalDate dob;


    private String phone;

    private String address;

    private String avatarUrl;

    public UpdateUserInformationRequest() {
    }

    public UpdateUserInformationRequest(Integer userId, Boolean gender, LocalDate dob, String phone, String address, String avatarUrl) {
        this.userId = userId;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.avatarUrl = avatarUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
