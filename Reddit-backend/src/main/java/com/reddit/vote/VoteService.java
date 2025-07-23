package com.reddit.vote;

import com.reddit.comment.Comment;
import com.reddit.content.Content;
import com.reddit.content.ContentService;
import com.reddit.exception.comment.CommentIsDeletedException;
import com.reddit.user.RedditUser;
import com.reddit.user.UserService;
import com.reddit.util.ErrorMessages;
import com.reddit.vote.dto.VoteDto;
import com.reddit.vote.dto.VotedContentDto;
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
    public VoteDto getVoteByContentAndUser(Long contentId, Long userId) {
        Content content = contentService.getContentEntity(contentId);
        RedditUser user = userService.getUserEntityById(userId);

        Optional<Vote> voteOptional = voteRepository.findByUserAndContent(user, content);

        return voteOptional
                .map(vote -> VoteMapper.voteToVoteDto(vote, vote.getVoteType().getScore()))
                .orElseGet(() -> new VoteDto(userId, contentId, VoteType.NO_VOTE, VoteType.NO_VOTE.getScore()));
    }

    @Transactional(readOnly = true)
    public Page<VotedContentDto> getUpVotedByUserId(Long userId, Pageable pageable) {
        return voteRepository
                .findByUserIdAndVoteType(userId, VoteType.UP_VOTE, pageable)
                .map(VoteMapper::voteToVotedContentDto);
    }

    @Transactional(readOnly = true)
    public Page<VotedContentDto> getDownVotedByUserId(Long userId, Pageable pageable) {
        return voteRepository
                .findByUserIdAndVoteType(userId, VoteType.DOWN_VOTE, pageable)
                .map(VoteMapper::voteToVotedContentDto);
    }
    public VoteDto toggleVote(VoteDto voteDto) {
        RedditUser user = userService.getUserEntityById(voteDto.userId());
        Content content = contentService.getContentEntity(voteDto.contentId());
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
        int score = voteType.getScore();

        Vote vote = new Vote();
        vote.setCreated(LocalDateTime.now());
        vote.setUser(user);
        vote.setContent(content);
        vote.setVoteType(voteType);
        content.setScore(content.getScore() + score);

        Vote savedVote = voteRepository.save(vote);

        return VoteMapper.voteToVoteDto(savedVote, score);
    }

    private VoteDto removeVote(Content content, Vote vote, VoteType voteType) {
        int score = -voteType.getScore();
        content.setScore(content.getScore() + score);

        voteRepository.deleteById(vote.getId());

        return new VoteDto(
                vote.getUser().getId(),
                content.getId(),
                VoteType.NO_VOTE,
                score
        );
    }

    private VoteDto changeVote(Content content, Vote vote, VoteType voteType) {
        int score = -vote.getVoteType().getScore() + voteType.getScore();
        content.setScore(content.getScore() + score);

        vote.setVoteType(voteType);

        Vote savedVote = voteRepository.save(vote);

        return VoteMapper.voteToVoteDto(savedVote, score);
    }
}
