package com.example.reddit.vote;

import com.example.reddit.content.Content;
import com.example.reddit.user.RedditUser;

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

    public static Vote voteDtoToVote(VoteDto voteDto) {
        if (voteDto == null) {
            return null;
        }

        Vote vote = new Vote();

        vote.setId(voteDto.id());
        vote.setVoteType(voteDto.voteType());

        return vote;
    }
}
