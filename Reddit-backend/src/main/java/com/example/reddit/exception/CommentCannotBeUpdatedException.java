package com.example.reddit.exception;

public class CommentCannotBeUpdatedException extends RuntimeException {
    public CommentCannotBeUpdatedException() {
    }

    public CommentCannotBeUpdatedException(String message) {
        super(message);
    }

    public CommentCannotBeUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentCannotBeUpdatedException(Throwable cause) {
        super(cause);
    }
}
