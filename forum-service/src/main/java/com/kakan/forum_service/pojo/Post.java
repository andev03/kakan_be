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
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    UUID id;

    @Column(name = "account_id", nullable = false)
    Integer accountId;

    @Column(columnDefinition = "TEXT", nullable = false)
    String content;

    @Column(name = "like_count", nullable = false)
    @Builder.Default
    int likeCount = 0;

    @Column(name = "comment_count", nullable = false)
    @Builder.Default
    int commentCount = 0;

    @Column(nullable = false)
    @Builder.Default
    String status = PostStatus.ACTIVE.toString();

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Comment> comments;
}
