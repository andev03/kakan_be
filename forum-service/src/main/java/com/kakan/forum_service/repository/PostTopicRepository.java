package com.kakan.forum_service.repository;

import com.kakan.forum_service.pojo.PostTopic;
import com.kakan.forum_service.pojo.PostTopicId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTopicRepository extends JpaRepository<PostTopic, PostTopicId> {
}
