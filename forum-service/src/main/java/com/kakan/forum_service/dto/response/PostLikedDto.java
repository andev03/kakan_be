package com.kakan.forum_service.dto.response;

import com.kakan.forum_service.dto.UserInformationDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PostLikedDto {
    UUID id;

    String title;

    List<String> accountNameLiked;

    String content;

    int likeCount;

    int commentCount;

    String status;

    LocalDateTime createdAt;

    List<String> topicName;

    boolean liked;

    UserInformationDto userInformationDto;
}
