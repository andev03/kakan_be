package com.kakan.forum_service.exception;

import java.util.UUID;

public class ReportNotFoundException extends RuntimeException{
    public ReportNotFoundException(UUID reportId) {
        super("Cannot find post by post id: " + reportId);
    }
}
