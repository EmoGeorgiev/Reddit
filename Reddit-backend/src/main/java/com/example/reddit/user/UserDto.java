package com.example.reddit.user;

import com.example.reddit.comment.CommentDto;
import com.example.reddit.post.PostDto;

import java.util.Set;

public record UserDto(
        Long id,
        String username,
        Set<PostDto> posts,
        Set<CommentDto> comments) {
}
