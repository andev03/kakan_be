package com.kakan.forum_service.controller;

import com.kakan.forum_service.dto.request.CommentPostRequestDto;
import com.kakan.forum_service.dto.response.ResponseDto;
import com.kakan.forum_service.service.CommentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    final CommentService commentService;

    @PostMapping("/comment/{postId}")
    public ResponseDto<Object> commentPostByPostId(@PathVariable UUID postId, @Valid @RequestBody CommentPostRequestDto commentPostRequestDto) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(commentService.commentPostByPostId(postId, commentPostRequestDto))
                .build();
    }

    @GetMapping("/comments/{postId}")
    public ResponseDto<Object> viewAllCommentByPostId(@PathVariable UUID postId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(commentService.viewAllCommentByPostId(postId))
                .build();
    }
}
