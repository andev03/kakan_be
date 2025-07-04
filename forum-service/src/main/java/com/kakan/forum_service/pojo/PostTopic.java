package com.kakan.forum_service.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "post_topic")
@IdClass(PostTopicId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostTopic {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    Post post;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName = "id", nullable = false)
    Topic topic;
}
