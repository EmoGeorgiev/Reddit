package com.example.reddit.subreddit;

import com.example.reddit.exception.SubredditNotFoundException;
import com.example.reddit.util.ErrorMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubredditService {
    private final SubredditRepository subredditRepository;

    public SubredditService(SubredditRepository subredditRepository) {
        this.subredditRepository = subredditRepository;
    }

    @Transactional(readOnly = true)
    public Subreddit getSubredditEntity(Long id) {
        return subredditRepository
                .findById(id)
                .orElseThrow(() -> new SubredditNotFoundException(ErrorMessages.SUBREDDIT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        return SubredditMapper.subredditToSubredditDto(getSubredditEntity(id));
    }

    public SubredditDto addSubreddit(SubredditDto subredditDto) {
        throw new UnsupportedOperationException();
    }


    public SubredditDto updateSubreddit(SubredditDto subredditDto) {
        throw new UnsupportedOperationException();
    }


    public SubredditDto deleteSubreddit(Long id) {
        throw new UnsupportedOperationException();
    }
}
