package com.kakan.forum_service.mapper;

import com.kakan.forum_service.dto.CommentDto;
import com.kakan.forum_service.pojo.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    List<CommentDto> toDtoList(List<Comment> comments);

    CommentDto toDto(Comment comment);
}
