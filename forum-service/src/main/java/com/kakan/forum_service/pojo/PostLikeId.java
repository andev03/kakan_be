package com.kakan.forum_service.pojo;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostLikeId implements Serializable {
    Post post;
    Integer accountId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostLikeId postLikeId)) return false;
        return Objects.equals(post, postLikeId.post) &&
                Objects.equals(accountId, postLikeId.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, accountId);
    }
}

