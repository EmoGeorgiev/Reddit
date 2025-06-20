package com.reddit.post;

import com.reddit.comment.CommentDto;
import com.reddit.comment.CommentMapper;
import com.reddit.user.RedditUser;
import com.reddit.vote.VoteDto;
import com.reddit.vote.VoteMapper;

import java.util.Set;
import java.util.stream.Collectors;

public class PostMapper {
    public static PostDto postToPostDto(Post post) {
        if (post == null) {
            return null;
        }

        Set<VoteDto> votes = post
                .getVotes()
                .stream()
                .map(VoteMapper::voteToVoteDto)
                .collect(Collectors.toSet());

        Set<Long> savedBy = post
                .getSavedBy()
                .stream()
                .map(RedditUser::getId)
                .collect(Collectors.toSet());

        Set<CommentDto> comments = post
                .getComments()
                .stream()
                .map(CommentMapper::commentToCommentDto)
                .collect(Collectors.toSet());

        return new PostDto(
            post.getId(),
            post.getUser().getId(),
            post.getContentType(),
            post.getCreated(),
            post.getText(),
            post.getScore(),
            votes,
            savedBy,
            post.getSubreddit().getId(),
            post.getTitle(),
            comments
        );
    }

    public static Post postDtoToPost(PostDto postDto) {
        if (postDto == null) {
            return null;
        }

        Post post = new Post();
        post.setId(postDto.id());
        post.setContentType(postDto.contentType());
        post.setCreated(postDto.created());
        post.setText(postDto.text());
        post.setTitle(postDto.title());

        return post;
    }
}
