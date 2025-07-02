package com.kakan.forum_service.service;

import com.kakan.forum_service.dto.UserInformationDto;

import java.util.List;

public interface CommonService {
    List<String> getAccountNameFromAccountService(List<Integer> accountIds);

    List<UserInformationDto> getAccountFromAccountService(List<Integer> accountIds);

    UserInformationDto getAccountByAccountIdFromAccountService(Integer accountId);
}
