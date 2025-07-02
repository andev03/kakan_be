package com.kakan.forum_service.service;

import com.kakan.forum_service.dto.PostDto;
import com.kakan.forum_service.dto.request.CreatePostRequestDto;
import com.kakan.forum_service.dto.response.PostLikedDto;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<PostDto> viewAllPostAdmin();

    List<PostLikedDto> viewAllPostUser(Integer accountId);

    List<PostDto> viewPostByAccountId(Integer accountId);

    PostDto likePostByPostId(UUID postId, Integer accountId);

    PostDto createPost(CreatePostRequestDto createPostRequestDto);

    PostDto viewPostByReportId(UUID reportId);

    PostDto blockPostByPostId(UUID postId);

    PostDto deletePostByPostId(UUID postId);

    PostLikedDto viewPostByPostId(UUID postId, Integer accountId);

    List<String> viewUserNameLiked(UUID postId);

    List<PostDto> viewAllPostUserLiked(Integer accountId);
}
