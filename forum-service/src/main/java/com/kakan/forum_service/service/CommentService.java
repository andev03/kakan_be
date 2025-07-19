package com.kakan.forum_service.service;

import com.kakan.forum_service.dto.CommentDto;
import com.kakan.forum_service.dto.request.CommentPostRequestDto;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    CommentDto commentPostByPostId(UUID postId, CommentPostRequestDto commentPostRequestDto);

    List<CommentDto> viewAllCommentByPostId(UUID postId);
}
