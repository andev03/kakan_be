package com.kakan.forum_service.dto;

import com.kakan.forum_service.pojo.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CommentDto {

    UUID id;

    Integer accountId;

    String content;

    LocalDateTime createdAt;
}
