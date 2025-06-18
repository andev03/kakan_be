package com.kakan.forum_service.repository;

import com.kakan.forum_service.pojo.PostLike;
import com.kakan.forum_service.pojo.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
}
