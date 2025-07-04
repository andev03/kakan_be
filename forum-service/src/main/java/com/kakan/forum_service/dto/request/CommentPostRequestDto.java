package com.kakan.forum_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentPostRequestDto {

    @Min(value = 1, message = "Chưa có account id")
    Integer accountId;

    @NotBlank(message = "Vui lòng nhập comment!")
    String message;
}
