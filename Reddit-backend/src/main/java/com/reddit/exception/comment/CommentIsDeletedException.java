package com.reddit.exception.comment;

public class CommentIsDeletedException extends RuntimeException {
    public CommentIsDeletedException() {
    }

    public CommentIsDeletedException(String message) {
        super(message);
    }

    public CommentIsDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentIsDeletedException(Throwable cause) {
        super(cause);
    }
}
