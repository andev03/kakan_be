package com.kakan.admission_advisor_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ChatMessageDto {
    UUID id;

    String senderType;

    Integer senderId;

    String content;

    LocalDateTime sentAt = LocalDateTime.now();

    LocalDateTime deletedAt;
}
