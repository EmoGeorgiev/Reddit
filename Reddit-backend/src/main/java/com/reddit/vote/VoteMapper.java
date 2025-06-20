package com.reddit.vote;

import com.reddit.content.Content;
import com.reddit.user.RedditUser;

public class VoteMapper {
    public static VoteDto voteToVoteDto(Vote vote) {
        if (vote == null) {
            return null;
        }

        return new VoteDto(
                vote.getId(),
                vote.getUser().getId(),
                vote.getContent().getId(),
                vote.getVoteType());
    }

    public static Vote voteDtoToVote(VoteDto voteDto, RedditUser user, Content content) {
        if (voteDto == null) {
            return null;
        }

        Vote vote = new Vote();

        vote.setId(voteDto.id());
        vote.setUser(user);
        vote.setContent(content);
        vote.setVoteType(voteDto.voteType());

        return vote;
    }
}
