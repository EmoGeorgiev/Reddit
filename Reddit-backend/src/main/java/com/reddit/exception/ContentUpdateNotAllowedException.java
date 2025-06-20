package com.reddit.exception;

public class ContentUpdateNotAllowedException extends RuntimeException {
    public ContentUpdateNotAllowedException() {
    }

    public ContentUpdateNotAllowedException(String message) {
        super(message);
    }

    public ContentUpdateNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentUpdateNotAllowedException(Throwable cause) {
        super(cause);
    }
}
