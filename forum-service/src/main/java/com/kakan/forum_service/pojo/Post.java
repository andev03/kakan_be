package com.kakan.forum_service.pojo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {

    @Id
    @GeneratedValue
    UUID id;

    @Column(name = "user_id", nullable = false)
    UUID userId;

    @Column(columnDefinition = "TEXT", nullable = false)
    String content;

    @Column(name = "like_count", nullable = false)
    int likeCount;

    @Column(name = "comment_count", nullable = false)
    int commentCount;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Comment> comments;
}
