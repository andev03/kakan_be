package com.kakan.admission_advisor_service.service.impl;

import com.kakan.admission_advisor_service.dto.ChatSessionDto;
import com.kakan.admission_advisor_service.mapper.ChatSessionMapper;
import com.kakan.admission_advisor_service.repository.ChatSessionRepository;
import com.kakan.admission_advisor_service.service.ChatSessionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ChatSessionServiceImpl implements ChatSessionService {

    final ChatSessionMapper chatSessionMapper;

    final ChatSessionRepository chatSessionRepository;

    @Override
    public List<ChatSessionDto> getAllChatSessionByAccountId(Integer accountId) {
        return chatSessionMapper.toDtoList(chatSessionRepository.findByAccountId(accountId));
    }
}
