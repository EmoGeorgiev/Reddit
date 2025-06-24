package com.reddit.vote;

import com.reddit.comment.Comment;
import com.reddit.content.Content;
import com.reddit.content.ContentService;
import com.reddit.exception.comment.CommentIsDeletedException;
import com.reddit.user.RedditUser;
import com.reddit.user.UserService;
import com.reddit.util.ErrorMessages;
import com.reddit.vote.dto.VoteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserService userService;
    private final ContentService contentService;

    public VoteService(VoteRepository voteRepository, UserService userService, ContentService contentService) {
        this.voteRepository = voteRepository;
        this.userService = userService;
        this.contentService = contentService;
    }

    @Transactional(readOnly = true)
    public Page<VoteDto> getUpVotedByUserId(Long userId, Pageable pageable) {
        return voteRepository
                .findByUserIdAndVoteType(userId, VoteType.UP_VOTE, pageable)
                .map(VoteMapper::voteToVoteDto);
    }

    @Transactional(readOnly = true)
    public Page<VoteDto> getDownVotedByUserId(Long userId, Pageable pageable) {
        return voteRepository
                .findByUserIdAndVoteType(userId, VoteType.DOWN_VOTE, pageable)
                .map(VoteMapper::voteToVoteDto);
    }
    public VoteDto toggleVote(VoteDto voteDto) {
        RedditUser user = userService.getUserEntity(voteDto.userId());
        Content content = contentService.getContentEntity(voteDto.contentDto().id());
        VoteType voteType = voteDto.voteType();

        if (content instanceof Comment c) {
            if (c.isDeleted()) {
                throw new CommentIsDeletedException(ErrorMessages.COMMENT_IS_DELETED);
            }
        }

        Optional<Vote> voteOptional = voteRepository.findByUserAndContent(user, content);

        if (voteOptional.isEmpty()) {
            return createVote(user, content, voteType);
        }
        Vote vote = voteOptional.get();

        if (vote.getVoteType() == voteType) {
            return removeVote(content, vote, voteType);
        }

        return changeVote(content, vote, voteType);
    }

    private VoteDto createVote(RedditUser user, Content content, VoteType voteType) {
        Vote vote = new Vote();
        vote.setCreated(LocalDateTime.now());
        vote.setUser(user);
        vote.setContent(content);
        vote.setVoteType(voteType);
        content.setScore(content.getScore() + voteType.getScore());

        Vote savedVote = voteRepository.save(vote);

        return VoteMapper.voteToVoteDto(savedVote);
    }

    private VoteDto removeVote(Content content, Vote vote, VoteType voteType) {
        content.setScore(content.getScore() - voteType.getScore());

        VoteDto voteDto = VoteMapper.voteToVoteDto(vote);

        voteRepository.deleteById(vote.getId());

        return voteDto;
    }

    private VoteDto changeVote(Content content, Vote vote, VoteType voteType) {
        content.setScore(content.getScore() - vote.getVoteType().getScore() + voteType.getScore());
        vote.setVoteType(voteType);

        Vote savedVote = voteRepository.save(vote);

        return VoteMapper.voteToVoteDto(savedVote);
    }
}
