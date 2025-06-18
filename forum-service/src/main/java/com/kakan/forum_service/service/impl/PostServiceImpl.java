package com.kakan.forum_service.service.impl;

import com.kakan.forum_service.dto.PostDto;
import com.kakan.forum_service.dto.request.CreatePostRequestDto;
import com.kakan.forum_service.enums.PostStatus;
import com.kakan.forum_service.exception.PostNotFoundException;
import com.kakan.forum_service.exception.ReportNotFoundException;
import com.kakan.forum_service.mapper.PostMapper;
import com.kakan.forum_service.pojo.Post;
import com.kakan.forum_service.pojo.Report;
import com.kakan.forum_service.repository.PostRepository;
import com.kakan.forum_service.repository.ReportRepository;
import com.kakan.forum_service.service.PostService;
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
public class PostServiceImpl implements PostService {

    final PostRepository postRepository;

    final PostMapper postMapper;

    final ReportRepository reportRepository;

    @Override
    public List<PostDto> viewAllPost() {
        return postMapper.toDtoList(postRepository.findAll());
    }

    @Override
    public List<PostDto> viewPostByAccountId(Integer accountId) {
        return postMapper.toDtoList(postRepository.findByAccountId(accountId));
    }

    @Override
    @Transactional
    public PostDto likePostByPostId(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostDto createPost(CreatePostRequestDto createPostRequestDto) {
        Post post = postRepository.save(
                Post.builder()
                        .accountId(createPostRequestDto.getAccountId())
                        .content(createPostRequestDto.getContent())
                        .build()
        );
        return postMapper.toDto(post);
    }

    @Override
    public PostDto viewPostByReportId(UUID reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new ReportNotFoundException(reportId));

        UUID postId = report.getPost().getId();

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostDto blockPostByPostId(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        post.setStatus(PostStatus.BLOCKED.name());

        post = postRepository.save(post);

        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostDto deletePostByPostId(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        post.setStatus(PostStatus.DELETED.name());

        post = postRepository.save(post);

        return postMapper.toDto(post);
    }
}
