package com.kakan.forum_service.repository;

import com.kakan.forum_service.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findByPostId(UUID postId);
}
