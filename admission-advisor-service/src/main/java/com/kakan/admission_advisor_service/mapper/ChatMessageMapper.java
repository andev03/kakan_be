package com.kakan.admission_advisor_service.mapper;

import com.kakan.admission_advisor_service.dto.ChatMessageDto;
import com.kakan.admission_advisor_service.pojo.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMessageMapper {

    List<ChatMessageDto> toDtoList(List<ChatMessage> chatMessage);

    ChatMessageDto toDto(ChatMessage chatMessage);
}
