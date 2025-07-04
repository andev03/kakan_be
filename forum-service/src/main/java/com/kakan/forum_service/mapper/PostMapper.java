package com.kakan.forum_service.mapper;

import com.kakan.forum_service.dto.PostDto;
import com.kakan.forum_service.dto.response.PostLikedDto;
import com.kakan.forum_service.pojo.Post;
import com.kakan.forum_service.pojo.PostTopic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(source = "postTopics", target = "topicName", qualifiedByName = "mapTopicNames")
    PostDto toDto(Post post);

    List<PostDto> toDtoList(List<Post> posts);

    @Mapping(target = "liked", expression = "java(liked)")
    PostLikedDto toPostLikedDto(Post post, boolean liked);

    default List<PostLikedDto> toPostLikedDtoListTrue(List<Post> postList) {
        return postList.stream()
                .map(post -> toPostLikedDto(post, true))
                .collect(Collectors.toList());
    }

    default List<PostLikedDto> toPostDtoListFalse(List<Post> postList) {
        return postList.stream()
                .map(post -> toPostLikedDto(post, false))
                .collect(Collectors.toList());
    }

    @Named("mapTopicNames")
    static List<String> mapTopicNames(List<PostTopic> postTopics) {
        if (postTopics == null) return new ArrayList<>();
        return postTopics.stream()
                .map(pt -> pt.getTopic().getName())
                .collect(Collectors.toList());
    }
}
