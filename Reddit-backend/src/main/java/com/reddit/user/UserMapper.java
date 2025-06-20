package com.reddit.user;

import com.reddit.comment.CommentDto;
import com.reddit.comment.CommentMapper;
import com.reddit.post.PostDto;
import com.reddit.post.PostMapper;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto userToUserDto(RedditUser user) {
        if (user == null) {
            return null;
        }

        Set<PostDto> posts = user
                .getPosts()
                .stream()
                .map(PostMapper::postToPostDto)
                .collect(Collectors.toSet());

        Set<CommentDto> comments = user
                .getComments()
                .stream()
                .map(CommentMapper::commentToCommentDto)
                .collect(Collectors.toSet());

        return new UserDto(
                user.getId(),
                user.getUsername(),
                posts,
                comments);
    }
}
