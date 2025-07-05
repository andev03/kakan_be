package com.kakan.forum_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {

    UUID id;

    String title;

    List<String> accountNameLiked;

    String content;

    int likeCount;

    int commentCount;

    String status;

    LocalDateTime createdAt;

    List<String> topicName;

    String accountName;
}
