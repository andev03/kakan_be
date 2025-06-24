package com.kakan.forum_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
public class PostDto {

    UUID id;

    String title;

    @Builder.Default
    String accountName = "Nguyen Van A";

    String content;

    int likeCount;

    int commentCount;

    String status;

    LocalDateTime createdAt;

    String topicName;
}
