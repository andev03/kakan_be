package com.kakan.forum_service.mapper;

import com.kakan.forum_service.dto.PostDto;
import com.kakan.forum_service.pojo.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(source = "topic.name", target = "topicName")
    PostDto toDto(Post post);

    List<PostDto> toDtoList(List<Post> posts);
}
