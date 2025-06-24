package com.reddit.comment;

import com.reddit.comment.dto.CommentDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {
    public static CommentDto commentToCommentDto(Comment comment) {
        if (comment == null) {
            return null;
        }

        List<CommentDto> replies = mapRepliesToDto(comment.getReplies());

        return new CommentDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getCreated(),
                comment.getText(),
                comment.getScore(),
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
        comment.setCreated(commentDto.created());
        comment.setText(commentDto.text());
        comment.setDeleted(commentDto.isDeleted());

        return comment;
    }

    private static List<CommentDto> mapRepliesToDto(List<Comment> replies) {
        if (replies == null) {
            return new ArrayList<>();
        }
        return replies
                .stream()
                .map(CommentMapper::commentToCommentDto)
                .collect(Collectors.toList());
    }
}
