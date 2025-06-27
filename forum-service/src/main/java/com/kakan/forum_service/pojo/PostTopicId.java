package com.kakan.forum_service.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostTopicId implements Serializable {
    UUID post;
    Integer topic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostTopicId that)) return false;
        return Objects.equals(post, that.post) && Objects.equals(topic, that.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, topic);
    }
}