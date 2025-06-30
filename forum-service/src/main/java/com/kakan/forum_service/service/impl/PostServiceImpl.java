package com.kakan.forum_service.service.impl;

import com.kakan.account.grpc.UserIdListRequest;
import com.kakan.account.grpc.UserListResponse;
import com.kakan.account.grpc.UserResponse;
import com.kakan.account.grpc.UserServiceGrpc;
import com.kakan.forum_service.dto.PostDto;
import com.kakan.forum_service.dto.request.CreatePostRequestDto;
import com.kakan.forum_service.dto.response.PostLikedDto;
import com.kakan.forum_service.enums.PostStatus;
import com.kakan.forum_service.exception.PostNotFoundException;
import com.kakan.forum_service.exception.ReportNotFoundException;
import com.kakan.forum_service.mapper.PostMapper;
import com.kakan.forum_service.pojo.*;
import com.kakan.forum_service.repository.*;
import com.kakan.forum_service.service.PostService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    final PostRepository postRepository;

    final PostMapper postMapper;

    final ReportRepository reportRepository;

    final PostTopicRepository postTopicRepository;

    final TopicRepository topicRepository;

    final PostLikeRepository postLikeRepository;

    @Override
    public List<PostDto> viewAllPostAdmin() {
        return postMapper.toDtoList(postRepository.findAll());
    }

    @Override
    public List<PostLikedDto> viewAllPostUser(Integer accountId) {

        List<PostLike> postLikeList = postLikeRepository.findByAccountId(accountId);

        List<PostDto> postList = postMapper.toDtoList(postRepository.findAll());

        return getPostFromPostLike(postLikeList, postList);
    }

    private List<PostLikedDto> getPostFromPostLike(List<PostLike> postLikeList, List<PostDto> postLists) {

        List<PostLikedDto> likedDtoList = new ArrayList<>();

        if (!postLikeList.isEmpty()) {
            for (PostDto postList : postLists) {
                for (PostLike postLike : postLikeList) {
                    if (postList.getId() == postLike.getPost().getId()) {
                        likedDtoList.add(postMapper.toPostLikedDto(postList, true));
                    } else {
                        likedDtoList.add(postMapper.toPostLikedDto(postList, false));
                    }
                }
            }
        } else {
            return postMapper.toPostDtoListFalse(postLists);
        }
        return likedDtoList;
    }

    @Override
    public List<PostDto> viewPostByAccountId(Integer accountId) {
        return postMapper.toDtoList(postRepository.findByAccountId(accountId));
    }

    @Override
    @Transactional
    public PostDto likePostByPostId(UUID postId, Integer accountId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        PostLikeId postLikeId = PostLikeId.builder()
                .accountId(accountId)
                .post(post)
                .build();

        PostLike postLike = PostLike.builder()
                .accountId(postLikeId.getAccountId())
                .post(postLikeId.getPost())
                .build();

        postLikeRepository.findById(postLikeId)
                .ifPresentOrElse(postLikes -> {
                    post.setLikeCount(post.getLikeCount() - 1);
                    postLikeRepository.delete(postLike);
                }, () -> {
                    post.setLikeCount(post.getLikeCount() + 1);
                    postLikeRepository.save(postLike);
                });

        postRepository.save(post);

        return postMapper.toDto(post);
    }


    @Override
    @Transactional
    public PostDto createPost(CreatePostRequestDto createPostRequestDto) {

        Post post = postRepository.save(
                Post.builder()
                        .accountId(createPostRequestDto.getAccountId())
                        .title(createPostRequestDto.getTitle())
                        .content(createPostRequestDto.getContent())
                        .build()
        );

        List<PostTopic> postTopic = savePostTopic(createPostRequestDto.getTopicId(), post);

        post.setPostTopics(postTopic);

        return postMapper.toDto(post);
    }

    @Transactional
    private List<PostTopic> savePostTopic(List<Integer> topicIds, Post post) {
        List<Topic> topics = getAllTopicByListId(topicIds);

        return postTopicRepository.saveAll(createPostTopicByTopic(topics, post));
    }

    private List<Topic> getAllTopicByListId(List<Integer> topicIds) {
        return topicRepository.findAllById(topicIds);
    }

    private List<PostTopic> createPostTopicByTopic(List<Topic> topics, Post post) {
        List<PostTopic> postTopics = new ArrayList<>();
        for (Topic topic : topics) {
            postTopics.add(
                    PostTopic.builder()
                            .topic(topic)
                            .post(post)
                            .build()
            );
        }

        return postTopics;
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

    @Override
    public PostDto viewPostByPostId(UUID postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        return postMapper.toDto(post);
    }

    @Override
    public List<String> viewUserNameLiked(UUID postId) {
        List<PostLike> postLikes = postLikeRepository.findByPost_Id(postId);

        List<Integer> accountIds = new ArrayList<>();

        for (PostLike postLike : postLikes) {
            accountIds.add(postLike.getAccountId());
        }
        return getAccountNameFromAccountService(accountIds);
    }

    @Override
    public List<PostDto> viewAllPostUserLiked(Integer accountId) {
        List<PostLike> postLikes = postLikeRepository.findByAccountId(accountId);

        return getPostFromPostLikeForAll(postLikes);
    }

    private List<PostDto> getPostFromPostLikeForAll(List<PostLike> postLikeList) {

        List<PostDto> postList = new ArrayList<>();

        for (PostLike postLike : postLikeList) {
            postList.add(postMapper.toDto(postLike.getPost()));
        }

        return postList;
    }

    private List<String> getAccountNameFromAccountService(List<Integer> accountIds) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        try {
            UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
            UserIdListRequest request = UserIdListRequest.newBuilder().addAllUserIds(accountIds).build();
            UserListResponse response = stub.getUsersByIds(request);

            return response.getUsersList().stream()
                    .map(UserResponse::getFullName)
                    .toList();
        } finally {
            channel.shutdown();
        }
    }
}
