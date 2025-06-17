package com.kakan.admission_advisor_service.service;

import com.kakan.admission_advisor_service.dto.request.ChatMessageRequestDto;
import com.kakan.admission_advisor_service.dto.ChatMessageDto;
import com.kakan.admission_advisor_service.dto.response.SendChatResponseDto;
import com.kakan.admission_advisor_service.pojo.ChatMessage;

import java.util.List;
import java.util.UUID;

public interface ChatMessageService {

    List<ChatMessageDto> getAllChatMessageByAccountIdAndSessionId(Integer accountId, UUID sessionId);

    SendChatResponseDto chatMessageWithAdmission(ChatMessageRequestDto chatMessageRequestDto);
}
