package com.kakan.forum_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PostDto {

    UUID id;

    Integer accountId;

    String content;

    int likeCount;

    int commentCount;

    String status;

    LocalDateTime createdAt;
}
