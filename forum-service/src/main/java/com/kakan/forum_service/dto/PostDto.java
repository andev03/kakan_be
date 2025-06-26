package com.kakan.forum_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
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

    public List<String> getAccountName() {
        if (accountNameLiked == null || accountNameLiked.isEmpty()) {
            return List.of("Nguyen Van A", "Nguyen Van B", "Nguyen Van C");
        }
        return accountNameLiked;
    }
}
