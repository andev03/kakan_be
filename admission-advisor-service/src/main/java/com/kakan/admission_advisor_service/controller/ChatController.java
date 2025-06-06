package com.kakan.admission_advisor_service.controller;

import com.kakan.admission_advisor_service.dto.response.ChatMessageResponseDto;
import com.kakan.admission_advisor_service.mapper.ChatMessageMapper;
import com.kakan.admission_advisor_service.service.ChatMessageService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@RestController
public class ChatController {

    final ChatMessageService chatMessageService;
    final ChatMessageMapper chatMessageMapper;

    public ChatController(ChatMessageService chatMessageService, ChatMessageMapper chatMessageMapper) {
        this.chatMessageService = chatMessageService;
        this.chatMessageMapper = chatMessageMapper;
    }

    @GetMapping("/chat/messages")
    public List<ChatMessageResponseDto> chatMessages() {
        return chatMessageMapper.toDtoList(chatMessageService.getAll());
    }
}
