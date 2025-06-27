package com.kakan.forum_service.repository;

import com.kakan.forum_service.pojo.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findByAccountId(Integer accountId);

    List<Post> findByIdNotIn(List<UUID> postId);
}
