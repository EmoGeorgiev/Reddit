package com.reddit.vote;

import com.reddit.content.Content;
import com.reddit.content.ContentMapper;
import com.reddit.user.RedditUser;
import com.reddit.vote.dto.VoteDto;
import com.reddit.vote.dto.VotedContentDto;

public class VoteMapper {
    public static VotedContentDto voteToVotedContentDto(Vote vote) {
        if (vote == null) {
            return null;
        }

        return new VotedContentDto(
                vote.getId(),
                vote.getCreated(),
                vote.getUser().getId(),
                ContentMapper.contentToContentDto(vote.getContent()),
                vote.getVoteType()
        );
    }

    public static Vote votedContentDtoToVote(VotedContentDto votedContentDto, RedditUser user, Content content) {
        if (votedContentDto == null) {
            return null;
        }

        Vote vote = new Vote();

        vote.setId(votedContentDto.id());
        vote.setCreated(votedContentDto.created());
        vote.setUser(user);
        vote.setContent(content);
        vote.setVoteType(votedContentDto.voteType());

        return vote;
    }

    public static VoteDto voteToVoteDto(Vote vote) {
        if (vote == null) {
            return null;
        }

        return new VoteDto(
                vote.getUser().getId(),
                vote.getContent().getId(),
                vote.getVoteType(),
                vote.getVoteType().getScore()
        );
    }
}
