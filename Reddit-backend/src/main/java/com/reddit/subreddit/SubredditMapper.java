package com.reddit.subreddit;

import com.reddit.subreddit.dto.SubredditDto;
import com.reddit.user.RedditUser;

import java.util.Set;
import java.util.stream.Collectors;

public class SubredditMapper {
    public static SubredditDto subredditToSubredditDto(Subreddit subreddit) {
        if (subreddit == null) {
            return null;
        }

        return new SubredditDto(
                subreddit.getId(),
                subreddit.getTitle(),
                subreddit.getUsers().size()
        );
    }

    public static Subreddit subredditDtoToSubreddit(SubredditDto subredditDto) {
        if (subredditDto == null) {
            return null;
        }

        Subreddit subreddit = new Subreddit();
        subreddit.setId(subredditDto.id());
        subreddit.setTitle(subredditDto.title());

        return subreddit;
    }
}
