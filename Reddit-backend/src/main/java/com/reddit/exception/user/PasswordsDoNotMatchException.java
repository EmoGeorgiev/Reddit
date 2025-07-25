package com.reddit.exception.user;

public class PasswordsDoNotMatchException extends RuntimeException {
    public PasswordsDoNotMatchException() {
    }

    public PasswordsDoNotMatchException(String message) {
        super(message);
    }

    public PasswordsDoNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordsDoNotMatchException(Throwable cause) {
        super(cause);
    }
}
