package com.kakan.forum_service.repository;

import com.kakan.forum_service.pojo.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}
