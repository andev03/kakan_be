package com.kakan.forum_service.controller;

import com.kakan.forum_service.dto.response.PostResponseDto;
import com.kakan.forum_service.mapper.PostMapper;
import com.kakan.forum_service.service.PostService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class PostController {

    final PostService postService;
    final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping("/posts")
    public List<PostResponseDto> postResponseDtos() {
        return postMapper.toDtoList(postService.getAll());
    }
}
