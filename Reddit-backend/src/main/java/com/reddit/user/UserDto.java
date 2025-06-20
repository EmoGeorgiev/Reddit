package com.reddit.user;

import com.reddit.comment.CommentDto;
import com.reddit.post.PostDto;

import java.util.Set;

public record UserDto(
        Long id,
        String username,
        Set<PostDto> posts,
        Set<CommentDto> comments) {
}
