package com.kakan.admission_advisor_service.service.impl;

import com.kakan.admission_advisor_service.pojo.ChatMessage;
import com.kakan.admission_advisor_service.repository.ChatMessageRepository;
import com.kakan.admission_advisor_service.service.ChatMessageService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageServiceImpl implements ChatMessageService {

    final ChatMessageRepository chatMessageRepository;

    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public List<ChatMessage> getAll() {
        return chatMessageRepository.findAll();
    }
}
