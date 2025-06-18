package com.kakan.forum_service.exception;

import java.util.UUID;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(UUID postId) {
        super("Cannot find post by post id: " + postId);
    }
}