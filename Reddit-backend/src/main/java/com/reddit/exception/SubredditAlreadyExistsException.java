package com.reddit.exception;

public class SubredditAlreadyExistsException extends RuntimeException {
    public SubredditAlreadyExistsException() {
    }

    public SubredditAlreadyExistsException(String message) {
        super(message);
    }

    public SubredditAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubredditAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
