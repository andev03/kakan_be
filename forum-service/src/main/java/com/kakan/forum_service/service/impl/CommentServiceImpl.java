package com.kakan.forum_service.service.impl;

import com.kakan.forum_service.dto.CommentDto;
import com.kakan.forum_service.dto.request.CommentPostRequestDto;
import com.kakan.forum_service.exception.PostNotFoundException;
import com.kakan.forum_service.mapper.CommentMapper;
import com.kakan.forum_service.pojo.Comment;
import com.kakan.forum_service.pojo.Post;
import com.kakan.forum_service.repository.CommentRepository;
import com.kakan.forum_service.repository.PostRepository;
import com.kakan.forum_service.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    final CommentRepository commentRepository;

    final PostRepository postRepository;

    final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentDto commentPostByPostId(UUID postId, CommentPostRequestDto commentPostRequestDto) {
        Post post = validatePostByPostId(postId);

        Comment comment = commentRepository.save(
                Comment.builder()
                        .post(post)
                        .accountId(commentPostRequestDto.getAccountId())
                        .content(commentPostRequestDto.getMessage())
                        .build()
        );
        countPostComment(postId);
        return commentMapper.toDto(comment);
    }

    @Override
    public List<CommentDto> viewAllCommentByPostId(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        return commentMapper.toDtoList(commentRepository.findByPostId(post.getId()));
    }

    private Post validatePostByPostId(UUID postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Transactional
    private void countPostComment(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        post.setCommentCount(post.getCommentCount() + 1);

        postRepository.save(post);
    }
}
