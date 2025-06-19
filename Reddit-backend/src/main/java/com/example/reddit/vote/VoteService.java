package com.example.reddit.vote;

import com.example.reddit.content.Content;
import com.example.reddit.content.ContentService;
import com.example.reddit.user.RedditUser;
import com.example.reddit.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void voteForContent(VoteDto voteDto) {
        RedditUser user = userService.getUserEntity(voteDto.userId());
        Content content = contentService.getContentEntity(voteDto.contentId());
        VoteType voteType = voteDto.voteType();

        Optional<Vote> voteOptional = voteRepository.findByUserAndContent(user, content);

        if (voteOptional.isEmpty()) {
            Vote vote = new Vote();
            vote.setUser(user);
            vote.setContent(content);
            vote.setVoteType(voteType);

            voteRepository.save(vote);

            content.setScore(content.getScore() + voteType.getScore());
            return;
        }
        Vote vote = voteOptional.get();

        if (vote.getVoteType() == voteType) {
            content.setScore(content.getScore() - voteType.getScore());
            voteRepository.deleteById(vote.getId());
        } else {
            content.setScore(content.getScore() - vote.getVoteType().getScore() + voteType.getScore());
            vote.setVoteType(voteType);
            voteRepository.save(vote);
        }
    }
}
