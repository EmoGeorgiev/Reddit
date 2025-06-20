package com.reddit.exception;

public class UserNotSubscribedException extends RuntimeException {
    public UserNotSubscribedException() {
    }

    public UserNotSubscribedException(String message) {
        super(message);
    }

    public UserNotSubscribedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotSubscribedException(Throwable cause) {
        super(cause);
    }
}
