package com.reddit.exception.user;

public class UserIsAlreadySubscribedToSubredditException extends RuntimeException {
    public UserIsAlreadySubscribedToSubredditException() {
    }

    public UserIsAlreadySubscribedToSubredditException(String message) {
        super(message);
    }

    public UserIsAlreadySubscribedToSubredditException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIsAlreadySubscribedToSubredditException(Throwable cause) {
        super(cause);
    }
}
