package com.reddit.util;

public final class PaginationConstants {
    private PaginationConstants() {
    }

    //Comment constants
    public static final int COMMENT_BY_POST_ID_SIZE = 200;
    public static final String COMMENT_BY_POST_ID_SORT = "score";
    public static final String COMMENT_BY_POST_ID_DIRECTION = "desc";
    public static final int COMMENT_BY_USER_ID_SIZE = 25;
    public static final String COMMENT_BY_USER_ID_SORT = "created";
    public static final String COMMENT_BY_USER_ID_DIRECTION = "desc";

    //Post constants
    public static final int POST_DEFAULT_SIZE = 25;
    public static final String POST_DEFAULT_SORT = "created";
    public static final String POST_DEFAULT_DIRECTION = "desc";

    //SavedContent constants
    public static final int SAVED_CONTENT_BY_USER_ID_SIZE = 25;
    public static final String SAVED_CONTENT_BY_USER_ID_SORT = "created";
    public static final String SAVED_CONTENT_BY_USER_ID_DIRECTION = "desc";

    //Subreddit constants
    public static final int SUBREDDIT_BY_TITLE_CONTAINS_SIZE = 25;
    public static final String SUBREDDIT_BY_TITLE_CONTAINS_SORT = "title";
    public static final String SUBREDDIT_BY_TITLE_CONTAINS_DIRECTION = "asc";

    //User constants
    public static final int USER_DEFAULT_SIZE = 25;
    public static final String USER_DEFAULT_SORT = "username";
    public static final String USER_DEFAULT_DIRECTION = "asc";

    //Vote constants
    public static final int VOTE_DEFAULT_SIZE = 25;
    public static final String VOTE_DEFAULT_SORT = "created";
    public static final String VOTE_DEFAULT_DIRECTION = "desc";
}
