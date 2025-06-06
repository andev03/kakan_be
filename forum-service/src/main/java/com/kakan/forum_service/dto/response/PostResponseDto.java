package com.kakan.forum_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PostResponseDto {
    UUID id;

    UUID userId;

    String content;

    int likeCount;

    int commentCount;

    LocalDateTime createdAt;
}
