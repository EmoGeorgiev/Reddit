package com.example.reddit.post;

import com.example.reddit.comment.CommentDto;
import com.example.reddit.comment.CommentMapper;
import com.example.reddit.user.RedditUser;
import com.example.reddit.vote.VoteDto;
import com.example.reddit.vote.VoteMapper;

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
