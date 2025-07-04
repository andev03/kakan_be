package com.kakan.forum_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {

    UUID id;

    Integer accountId;

    String accountName;

    String content;

    LocalDateTime createdAt;
}
