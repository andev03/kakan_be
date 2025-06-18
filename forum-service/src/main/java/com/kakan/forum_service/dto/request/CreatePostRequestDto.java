package com.kakan.forum_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePostRequestDto {

    @Min(value = 1, message = "Chưa có account id")
    Integer accountId;

    @NotBlank(message = "Chưa có content")
    String content;
}
