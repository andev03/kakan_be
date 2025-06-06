package com.kakan.admission_advisor_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageResponseDto {

    private String senderType;

    private String content;

    private LocalDateTime sentAt = LocalDateTime.now();

    private LocalDateTime deletedAt;
}
