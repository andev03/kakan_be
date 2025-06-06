package com.kakan.admission_advisor_service.mapper;

import com.kakan.admission_advisor_service.dto.response.ChatMessageResponseDto;
import com.kakan.admission_advisor_service.pojo.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMessageMapper {
    ChatMessageResponseDto toDto(ChatMessage chatMessage);

    List<ChatMessageResponseDto> toDtoList(List<ChatMessage> chatMessage);
}
