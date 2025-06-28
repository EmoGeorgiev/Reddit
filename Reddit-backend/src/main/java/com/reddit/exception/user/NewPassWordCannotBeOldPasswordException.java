package com.reddit.exception.user;

public class NewPassWordCannotBeOldPasswordException extends RuntimeException {
    public NewPassWordCannotBeOldPasswordException() {
    }

    public NewPassWordCannotBeOldPasswordException(String message) {
        super(message);
    }

    public NewPassWordCannotBeOldPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewPassWordCannotBeOldPasswordException(Throwable cause) {
        super(cause);
    }
}
