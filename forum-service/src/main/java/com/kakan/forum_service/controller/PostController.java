package com.kakan.forum_service.controller;


import com.kakan.forum_service.dto.request.CreatePostRequestDto;
import com.kakan.forum_service.dto.response.ResponseDto;
import com.kakan.forum_service.service.PostService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;


import org.springframework.web.bind.annotation.RestController;


@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@AllArgsConstructor
@RequestMapping("/api")
public class PostController {

    final PostService postService;

    @GetMapping("/posts/admin")
    public ResponseDto<Object> viewAllPostAdmin() {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(postService.viewAllPostAdmin())
                .build();
    }

    @GetMapping("/posts/{accountId}")
    public ResponseDto<Object> viewPostByAccountId(@PathVariable Integer accountId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(postService.viewPostByAccountId(accountId))
                .build();
    }

    @PutMapping("/post/{postId}/{accountId}/like")
    public ResponseDto<Object> likePostByPostId(@PathVariable UUID postId, @PathVariable Integer accountId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(postService.likePostByPostId(postId, accountId))
                .build();
    }

    @PostMapping("/post")
    public ResponseDto<Object> createPost(@Valid @RequestBody CreatePostRequestDto createPostRequestDto) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(postService.createPost(createPostRequestDto))
                .build();
    }

    @GetMapping("/post/report/{reportId}")
    public ResponseDto<Object> viewPostByReportId(@PathVariable UUID reportId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(postService.viewPostByReportId(reportId))
                .build();
    }

    @PutMapping("/post/{postId}/block")
    public ResponseDto<Object> blockPostByPostId(@PathVariable UUID postId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(postService.blockPostByPostId(postId))
                .build();
    }

    @DeleteMapping("/post/{postId}")
    public ResponseDto<Object> deletePostByPostId(@PathVariable UUID postId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(postService.deletePostByPostId(postId))
                .build();
    }

    @GetMapping("/post/{postId}")
    public ResponseDto<Object> viewPostByPostId(@PathVariable UUID postId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(postService.viewPostByPostId(postId))
                .build();
    }

    @GetMapping("/post/{postId}/like")
    public ResponseDto<Object> viewUserNameLiked(@PathVariable UUID postId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(postService.viewUserNameLiked(postId))
                .build();
    }
}
