package com.kakan.forum_service.pojo;

import com.kakan.forum_service.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "post")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {

    @Id
    @GeneratedValue
    UUID id;

    @Column(name = "account_id", nullable = false)
    Integer accountId;

    @Column(name = "title", nullable = false, length = 255)
    String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PostTopic> postTopics;

    @Column(name = "like_count", nullable = false)
    @Builder.Default
    int likeCount = 0;

    @Column(name = "comment_count", nullable = false)
    @Builder.Default
    int commentCount = 0;

    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    String status = PostStatus.ACTIVE.name();

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    @Builder.Default
    LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<PostLike> likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Report> reports;
}
