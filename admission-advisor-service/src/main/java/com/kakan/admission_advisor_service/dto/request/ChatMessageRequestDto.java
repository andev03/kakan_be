package com.kakan.admission_advisor_service.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ChatMessageRequestDto {

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin")
    @Min(value = 1, message = "Vui lòng nhập đầy đủ thông tin")
    Integer senderId;

    UUID sessionId = UUID.randomUUID();

    @NotBlank(message = "Tin nhắn trống")
    String message;

    @JsonCreator
    public ChatMessageRequestDto(
            @JsonProperty("senderId") Integer senderId,
            @JsonProperty("sessionId") UUID sessionId,
            @JsonProperty("message") String message
    ) {
        this.senderId = senderId;
        this.sessionId = sessionId != null ? sessionId : UUID.randomUUID();
        this.message = message;
    }
}
