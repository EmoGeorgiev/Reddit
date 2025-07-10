package com.reddit.content;

import com.reddit.comment.Comment;
import com.reddit.content.dto.ContentDto;
import com.reddit.post.Post;
import com.reddit.subreddit.SubredditMapper;
import com.reddit.subreddit.dto.SubredditDto;
import com.reddit.user.UserMapper;

public class ContentMapper {
    private static final String EMPTY_TITLE = "[empty]";
    private static final String EMPTY_TEXT = "";
    public static ContentDto contentToContentDto(Content content) {
        if (content == null) {
            return null;
        }

        return new ContentDto(
                content.getId(),
                UserMapper.userToUserDto(content.getUser()),
                content.getContentType(),
                content.getCreated(),
                getTitle(content),
                getText(content),
                content.getScore(),
                getSubreddit(content)
        );
    }

    private static String getTitle(Content content) {
        if (content instanceof Post p) {
            return p.getTitle();
        }
        if (content instanceof Comment c) {
            return c.getPost().getTitle();
        }
        return EMPTY_TITLE;
    }

    private static String getText(Content content) {
        if (content instanceof Post p) {
            return p.getDescription();
        }
        if (content instanceof Comment c) {
            return c.getText();
        }
        return EMPTY_TEXT;
    }

    private static SubredditDto getSubreddit(Content content) {
        if (content instanceof Post p) {
            return SubredditMapper.subredditToSubredditDto(p.getSubreddit());
        }
        if (content instanceof Comment c) {
            return SubredditMapper.subredditToSubredditDto(c.getPost().getSubreddit());
        }
        return null;
    }
}
