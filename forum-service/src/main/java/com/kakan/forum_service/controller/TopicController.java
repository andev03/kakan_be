package com.kakan.forum_service.controller;

import com.kakan.forum_service.dto.response.ResponseDto;
import com.kakan.forum_service.service.TopicService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@RequestMapping("/api")
@RequiredArgsConstructor
public class TopicController {

    final TopicService topicService;

    @GetMapping("/topics")
    public ResponseDto<Object> viewAllTopic() {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(topicService.getAllTopic())
                .build();
    }
}
