package com.reddit.post;

import com.reddit.post.dto.PostDto;
import com.reddit.subreddit.SubredditMapper;
import com.reddit.user.UserMapper;

public class PostMapper {
    public static PostDto postToPostDto(Post post) {
        if (post == null) {
            return null;
        }

        return new PostDto(
            post.getId(),
            UserMapper.userToUserDto(post.getUser()),
            post.getCreated(),
            post.getText(),
            post.getScore(),
            post.getComments().size(),
            SubredditMapper.subredditToSubredditDto(post.getSubreddit()),
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
