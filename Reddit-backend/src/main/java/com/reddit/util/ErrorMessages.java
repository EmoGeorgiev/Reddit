package com.reddit.util;

public final class ErrorMessages {
    private ErrorMessages() {
    }
    public static final String COMMENT_NOT_FOUND = "There does not exist a comment with this id";
    public static final String COMMENT_IS_DELETED = "The comment you are trying access is deleted";
    public static final String CONTENT_NOT_FOUND = "There does not exist a content with this id";
    public static final String CONTENT_UPDATE_NOT_ALLOWED = "The user is not the owner of the content";
    public static final String POST_NOT_FOUND = "There does not exist a post with this id";
    public static final String PASSWORDS_DO_NOT_MATCH = "The passwords do not match";
    public static final String NEW_PASSWORD_CANNOT_BE_OLD_PASSWORD = "The new password can not be the same as the old password";
    public static final String SUBREDDIT_ALREADY_EXISTS = "There already exists a subreddit with this name";
    public static final String SUBREDDIT_NOT_FOUND = "There does not exist a subreddit with this id";
    public static final String MISSING_MODERATOR_PRIVILEGES = "The user is missing moderator privileges";
    public static final String USER_NOT_FOUND = "There does not exist a user with this id";
    public static final String USERNAME_ALREADY_EXISTS = "There already exists a user with this username";
    public static final String USERNAME_DOES_NOT_EXIST = "There does not exist a user with this username";
    public static final String USER_NOT_SUBSCRIBED = "The user is not subscribed to this subreddit";
    public static final String USER_ALREADY_SUBSCRIBED = "The user is already subscribed to this subreddit";
}
