package com.reddit.vote;

import com.reddit.content.Content;
import com.reddit.content.ContentMapper;
import com.reddit.user.RedditUser;
import com.reddit.vote.dto.VoteDto;

public class VoteMapper {
    public static VoteDto voteToVoteDto(Vote vote) {
        if (vote == null) {
            return null;
        }

        return new VoteDto(
                vote.getId(),
                vote.getCreated(),
                vote.getUser().getId(),
                ContentMapper.contentToContentDto(vote.getContent()),
                vote.getVoteType());
    }

    public static Vote voteDtoToVote(VoteDto voteDto, RedditUser user, Content content) {
        if (voteDto == null) {
            return null;
        }

        Vote vote = new Vote();

        vote.setId(voteDto.id());
        vote.setCreated(voteDto.created());
        vote.setUser(user);
        vote.setContent(content);
        vote.setVoteType(voteDto.voteType());

        return vote;
    }
}
