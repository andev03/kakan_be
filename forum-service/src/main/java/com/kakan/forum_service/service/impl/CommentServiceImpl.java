package com.kakan.forum_service.service.impl;

import com.kakan.forum_service.dto.CommentDto;
import com.kakan.forum_service.dto.UserInformationDto;
import com.kakan.forum_service.dto.request.CommentPostRequestDto;
import com.kakan.forum_service.exception.PostNotFoundException;
import com.kakan.forum_service.mapper.CommentMapper;
import com.kakan.forum_service.pojo.Comment;
import com.kakan.forum_service.pojo.Post;
import com.kakan.forum_service.repository.CommentRepository;
import com.kakan.forum_service.repository.PostRepository;
import com.kakan.forum_service.service.CommentService;
import com.kakan.forum_service.service.CommonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    final CommentRepository commentRepository;

    final PostRepository postRepository;

    final CommentMapper commentMapper;

    final CommonService commonService;

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

        List<Comment> comments = commentRepository.findByPostId(post.getId());

        List<UserInformationDto> userInformationDtoList = commonService.getAccountFromAccountService(getAccountIdsFromComments(comments));

        Map<Integer, UserInformationDto> userInfoMap = userInformationDtoList.stream()
                .collect(Collectors.toMap(UserInformationDto::getAccountId, Function.identity()));

        return comments.stream()
                .map(comment -> {
                    CommentDto dto = commentMapper.toDto(comment);
                    UserInformationDto userInfo = userInfoMap.get(comment.getAccountId());
                    if (userInfo != null) {
                        dto.setAccountName(userInfo.getFullName());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private List<Integer> getAccountIdsFromComments(List<Comment> comments) {
        List<Integer> accountIds = new ArrayList<>();
        for (Comment comment : comments) {
            accountIds.add(comment.getAccountId());
        }
        return accountIds;
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
