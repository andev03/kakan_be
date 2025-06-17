package com.kakan.admission_advisor_service.mapper;

import com.kakan.admission_advisor_service.dto.ChatSessionDto;
import com.kakan.admission_advisor_service.pojo.ChatSession;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatSessionMapper {

    List<ChatSessionDto> toDtoList(List<ChatSession> sessions);

    ChatSessionDto toDto(ChatSession chatSession);
}
