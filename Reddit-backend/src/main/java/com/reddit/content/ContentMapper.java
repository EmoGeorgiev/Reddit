package com.reddit.content;

import com.reddit.comment.Comment;
import com.reddit.content.dto.ContentDto;
import com.reddit.post.Post;
import com.reddit.user.UserMapper;

public class ContentMapper {
    private static final String EMPTY_TITLE = "[empty]";
    public static ContentDto contentToContentDto(Content content) {
        if (content == null) {
            return null;
        }

        return new ContentDto(
                content.getId(),
                UserMapper.userToUserDto(content.getUser()),
                content.getCreated(),
                getTitle(content),
                content.getText(),
                content.getScore()
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
}
