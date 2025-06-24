package com.kakan.forum_service.repository;

import com.kakan.forum_service.pojo.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
}
