package com.reddit.subreddit;

import com.reddit.post.PostDto;
import com.reddit.post.PostMapper;
import com.reddit.user.RedditUser;

import java.util.Set;
import java.util.stream.Collectors;

public class SubredditMapper {
    public static SubredditDto subredditToSubredditDto(Subreddit subreddit) {
        if (subreddit == null) {
            return null;
        }

        Set<PostDto> posts = subreddit
                .getPosts()
                .stream()
                .map(PostMapper::postToPostDto)
                .collect(Collectors.toSet());

        Set<Long> users = subreddit
                .getUsers()
                .stream()
                .map(RedditUser::getId)
                .collect(Collectors.toSet());

        Set<Long> moderators = subreddit
                .getModerators()
                .stream()
                .map(RedditUser::getId)
                .collect(Collectors.toSet());

        return new SubredditDto(
                subreddit.getId(),
                subreddit.getTitle(),
                posts,
                users,
                moderators
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
