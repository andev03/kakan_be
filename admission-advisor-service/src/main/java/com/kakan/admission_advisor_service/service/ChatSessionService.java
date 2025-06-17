package com.kakan.admission_advisor_service.service;

import com.kakan.admission_advisor_service.dto.ChatSessionDto;

import java.util.List;

public interface ChatSessionService {

    List<ChatSessionDto> getAllChatSessionByAccountId(Integer accountId);


}
