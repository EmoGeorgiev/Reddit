package com.reddit.comment;

import com.reddit.user.RedditUser;
import com.reddit.vote.VoteDto;
import com.reddit.vote.VoteMapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CommentMapper {
    public static CommentDto commentToCommentDto(Comment comment) {
        if (comment == null) {
            return null;
        }

        Set<VoteDto> votes = comment
                .getVotes()
                .stream()
                .map(VoteMapper::voteToVoteDto)
                .collect(Collectors.toSet());

        Set<Long> savedBy = comment
                .getSavedBy()
                .stream()
                .map(RedditUser::getId)
                .collect(Collectors.toSet());

        Set<CommentDto> replies = mapRepliesToDto(comment.getReplies());

        return new CommentDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getContentType(),
                comment.getCreated(),
                comment.getText(),
                comment.getScore(),
                votes,
                savedBy,
                comment.isDeleted(),
                replies,
                comment.getParent().getId(),
                comment.getPost().getId()
        );
    }

    public static Comment commentDtoToComment(CommentDto commentDto) {
        if (commentDto == null) {
            return null;
        }

        Comment comment = new Comment();
        comment.setId(commentDto.id());
        comment.setContentType(commentDto.contentType());
        comment.setCreated(commentDto.created());
        comment.setText(commentDto.text());
        comment.setDeleted(commentDto.isDeleted());

        return comment;
    }

    private static Set<CommentDto> mapRepliesToDto(Set<Comment> replies) {
        if (replies == null) {
            return new HashSet<>();
        }
        return replies
                .stream()
                .map(CommentMapper::commentToCommentDto)
                .collect(Collectors.toSet());
    }
}
