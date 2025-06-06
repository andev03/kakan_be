package com.kakan.forum_service.mapper;

import com.kakan.forum_service.dto.response.PostResponseDto;
import com.kakan.forum_service.pojo.Post;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    PostResponseDto toDto(Post post);

    List<PostResponseDto> toDtoList(List<Post> posts);
}
