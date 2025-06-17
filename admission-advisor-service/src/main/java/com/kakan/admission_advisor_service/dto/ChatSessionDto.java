package com.kakan.admission_advisor_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ChatSessionDto {
    UUID id;

    String title;

    Integer accountId;

    LocalDateTime startedAt;

    String status = "active";
}
