package com.reddit.exception;

public class ModeratorCannotRemoveHimselfException extends RuntimeException {
    public ModeratorCannotRemoveHimselfException() {
    }

    public ModeratorCannotRemoveHimselfException(String message) {
        super(message);
    }

    public ModeratorCannotRemoveHimselfException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModeratorCannotRemoveHimselfException(Throwable cause) {
        super(cause);
    }
}
