package com.kakan.forum_service.service;

import com.kakan.forum_service.dto.PostDto;
import com.kakan.forum_service.dto.request.CreatePostRequestDto;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<PostDto> viewAllPost();

    List<PostDto> viewPostByAccountId(Integer accountId);

    PostDto likePostByPostId(UUID postId);

    PostDto createPost(CreatePostRequestDto createPostRequestDto);

    PostDto viewPostByReportId(UUID reportId);

    PostDto blockPostByPostId(UUID postId);

    PostDto deletePostByPostId(UUID postId);

    PostDto viewPostByPostId(UUID postId);
}
