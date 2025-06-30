package com.kakan.admission_advisor_service.dto.response;

import com.kakan.admission_advisor_service.dto.ChatMessageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SendChatResponseDto {

    ChatMessageDto chatUserDto;

    ChatMessageDto chatAI;
}
