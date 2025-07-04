package com.kakan.forum_service.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_like")
@IdClass(PostLikeId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostLike {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    Post post;

    @Id
    @Column(name = "account_id", nullable = false)
    Integer accountId;

    @Column(name = "liked_at", nullable = false)
    @Builder.Default
    LocalDateTime likedAt = LocalDateTime.now();
}

