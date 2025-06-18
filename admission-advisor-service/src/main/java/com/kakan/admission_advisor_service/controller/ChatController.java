package com.kakan.admission_advisor_service.controller;

import com.kakan.admission_advisor_service.dto.request.ChatMessageRequestDto;
import com.kakan.admission_advisor_service.dto.ChatMessageDto;
import com.kakan.admission_advisor_service.dto.ChatSessionDto;
import com.kakan.admission_advisor_service.dto.response.ResponseDto;
import com.kakan.admission_advisor_service.service.ChatMessageService;
import com.kakan.admission_advisor_service.service.ChatSessionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

    final ChatSessionService chatSessionService;
    final ChatMessageService chatMessageService;

    @GetMapping("/chat-sessions/{userId}")
    public ResponseDto<Object> chatSessionByUserId(@PathVariable Integer userId) {
        List<ChatSessionDto> chatSessionDto = chatSessionService.getAllChatSessionByAccountId(userId);
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(chatSessionDto)
                .build();
    }

    @GetMapping("/chat-messages/{accountId}/{sessionId}")
    public ResponseDto<Object> chatMessagesByUserIdAndSessionId(@PathVariable Integer accountId, @PathVariable UUID sessionId) {
        List<ChatMessageDto> chatMessageDto = chatMessageService.getAllChatMessageByAccountIdAndSessionId(accountId, sessionId);
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(chatMessageDto)
                .build();
    }

    @PostMapping("/chat-messages")
    public ResponseDto<Object> chatMessages(@Valid @RequestBody ChatMessageRequestDto chatMessageRequestDto) {

        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(chatMessageService.chatMessageWithAdmission(chatMessageRequestDto))
                .build();
    }
}
