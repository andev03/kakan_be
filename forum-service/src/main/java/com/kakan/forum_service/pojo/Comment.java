package com.kakan.forum_service.pojo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {

    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    Post post;

    @Column(name = "user_id", nullable = false)
    UUID userId;

    @Column(columnDefinition = "TEXT", nullable = false)
    String content;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

}
