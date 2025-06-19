package com.example.reddit.subreddit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubredditService {
    private final SubredditRepository subredditRepository;

    public SubredditService(SubredditRepository subredditRepository) {
        this.subredditRepository = subredditRepository;
    }
}
