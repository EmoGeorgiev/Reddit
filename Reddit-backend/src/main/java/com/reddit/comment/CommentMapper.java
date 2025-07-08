package com.reddit.comment;

import com.reddit.comment.dto.CommentDto;
import com.reddit.user.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {
    public static CommentDto commentToCommentDto(Comment comment) {
        if (comment == null) {
            return null;
        }

        List<CommentDto> replies = mapRepliesToDto(comment.getReplies());
        Long parentId = comment.getParent() != null ? comment.getParent().getId() : null;

        return new CommentDto(
                comment.getId(),
                UserMapper.userToUserDto(comment.getUser()),
                comment.getCreated(),
                comment.getText(),
                comment.getScore(),
                comment.isDeleted(),
                replies,
                parentId,
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
