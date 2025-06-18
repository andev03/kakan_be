package com.kakan.forum_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ReportDto {
    UUID id;

    Integer reporterId;

    String reason;

    LocalDateTime createdAt;
}
