package com.kakan.forum_service.dto.response;

import com.kakan.forum_service.pojo.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ViewPostResponse {


    UUID id;

    Integer accountId;

    String content;

    int likeCount;

    int commentCount;

    LocalDateTime createdAt;
}
