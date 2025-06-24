package com.reddit.post;

import com.reddit.post.dto.PostDto;

public class PostMapper {
    public static PostDto postToPostDto(Post post) {
        if (post == null) {
            return null;
        }

        return new PostDto(
            post.getId(),
            post.getUser().getId(),
            post.getCreated(),
            post.getText(),
            post.getScore(),
            post.getSubreddit().getId(),
            post.getTitle()
        );
    }

    public static Post postDtoToPost(PostDto postDto) {
        if (postDto == null) {
            return null;
        }

        Post post = new Post();
        post.setId(postDto.id());
        post.setCreated(postDto.created());
        post.setText(postDto.text());
        post.setTitle(postDto.title());

        return post;
    }
}
