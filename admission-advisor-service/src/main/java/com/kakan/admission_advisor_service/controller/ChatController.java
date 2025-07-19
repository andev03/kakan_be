package com.kakan.admission_advisor_service.controller;

import com.kakan.admission_advisor_service.dto.request.ChatMessageRequestDto;
import com.kakan.admission_advisor_service.dto.response.ResponseDto;
import com.kakan.admission_advisor_service.service.ChatMessageService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

    final ChatMessageService chatMessageService;

    @PostMapping("/chat-messages")
    public ResponseDto<Object> chatMessages(@Valid @RequestBody ChatMessageRequestDto chatMessageRequestDto) {

        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(chatMessageService.chatMessageWithAdmission(chatMessageRequestDto))
                .build();
    }
}
