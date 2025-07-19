package com.kakan.admission_advisor_service.service;

import com.kakan.admission_advisor_service.dto.request.ChatMessageRequestDto;
import com.kakan.admission_advisor_service.dto.response.SendChatResponseDto;

public interface ChatMessageService {

    SendChatResponseDto chatMessageWithAdmission(ChatMessageRequestDto chatMessageRequestDto);
}
