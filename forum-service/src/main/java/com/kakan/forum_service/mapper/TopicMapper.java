package com.kakan.forum_service.mapper;

import com.kakan.forum_service.dto.TopicDto;
import com.kakan.forum_service.pojo.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TopicMapper {
    List<TopicDto> toDtoList(List<Topic> comments);

    TopicDto toDto(Topic comment);
}
