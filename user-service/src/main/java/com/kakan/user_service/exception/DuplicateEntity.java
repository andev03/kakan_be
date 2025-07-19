package com.kakan.user_service.exception;

public class DuplicateEntity extends RuntimeException {
    public DuplicateEntity(String message) {
        super(message);
    }
}
