package com.kakan.forum_service.exception;

import com.kakan.forum_service.pojo.PostLikeId;

import java.util.UUID;

public class PostLikeNotFoundException extends RuntimeException {

    public PostLikeNotFoundException(PostLikeId postLikeId) {
        super("Cannot find post by post id: " + postLikeId.getPost() + " and account id: " + postLikeId.getAccountId());
    }
}
