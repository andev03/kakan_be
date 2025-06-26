package com.kakan.forum_service.repository;

import com.kakan.forum_service.pojo.PostLike;
import com.kakan.forum_service.pojo.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {

    List<PostLike> findByPost_Id(UUID postId);
}
