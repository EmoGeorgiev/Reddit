package com.reddit.exception.subreddit;

public class MissingModeratorPrivilegesException extends RuntimeException {
    public MissingModeratorPrivilegesException() {
    }

    public MissingModeratorPrivilegesException(String message) {
        super(message);
    }

    public MissingModeratorPrivilegesException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingModeratorPrivilegesException(Throwable cause) {
        super(cause);
    }
}
