package com.kakan.forum_service.service.impl;

import com.kakan.forum_service.dto.TopicDto;
import com.kakan.forum_service.mapper.TopicMapper;
import com.kakan.forum_service.repository.TopicRepository;
import com.kakan.forum_service.service.TopicService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopicServiceImpl implements TopicService {

    final TopicRepository topicRepository;
    final TopicMapper topicMapper;

    @Override
    public List<TopicDto> getAllTopic() {
        return topicMapper.toDtoList(topicRepository.findAll());
    }
}
