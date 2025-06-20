package com.example.reddit.subreddit;

import com.example.reddit.exception.MissingModeratorPrivilegesException;
import com.example.reddit.exception.ModeratorCannotRemoveHimselfException;
import com.example.reddit.exception.SubredditNotFoundException;
import com.example.reddit.exception.UserNotSubscribedException;
import com.example.reddit.user.RedditUser;
import com.example.reddit.user.UserService;
import com.example.reddit.util.ErrorMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final UserService userService;

    public SubredditService(SubredditRepository subredditRepository, UserService userService) {
        this.subredditRepository = subredditRepository;
        this.userService = userService;
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

    public SubredditDto addSubreddit(SubredditDto subredditDto, Long creatorId) {
        RedditUser user = userService.getUserEntity(creatorId);

        Subreddit subreddit = new Subreddit();
        subreddit.setTitle(subredditDto.title());
        subreddit.getUsers().add(user);
        subreddit.getModerators().add(user);

        Subreddit savedSubreddit = subredditRepository.save(subreddit);

        return SubredditMapper.subredditToSubredditDto(savedSubreddit);
    }

    public SubredditDto updateSubredditTitle(Long subredditId, String newTitle, Long moderatorId) {
        Subreddit subreddit = getSubredditEntity(subredditId);
        RedditUser user = userService.getUserEntity(moderatorId);

        if (!subreddit.getModerators().contains(user)) {
            throw new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES);
        }

        subreddit.setTitle(newTitle);

        Subreddit savedSubreddit = subredditRepository.save(subreddit);

        return SubredditMapper.subredditToSubredditDto(savedSubreddit);
    }

    public SubredditDto addSubredditModerator(Long subredditId, Long moderatorId, Long newModeratorId) {
        Subreddit subreddit = getSubredditEntity(subredditId);
        RedditUser moderator = userService.getUserEntity(moderatorId);
        RedditUser newModerator = userService.getUserEntity(newModeratorId);

        if (!subreddit.getModerators().contains(moderator)) {
            throw new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES);
        }

        if (!subreddit.getUsers().contains(newModerator)) {
            throw new UserNotSubscribedException(ErrorMessages.USER_NOT_SUBSCRIBED);
        }

        subreddit.getModerators().add(newModerator);

        Subreddit savedSubreddit = subredditRepository.save(subreddit);

        return SubredditMapper.subredditToSubredditDto(savedSubreddit);
    }

    public SubredditDto removeSubredditModerator(Long subredditId, Long moderatorId, Long removedModeratorId) {
        Subreddit subreddit = getSubredditEntity(subredditId);
        RedditUser moderator = userService.getUserEntity(moderatorId);
        RedditUser removedModerator = userService.getUserEntity(removedModeratorId);

        if (moderator.equals(removedModerator)) {
            throw new ModeratorCannotRemoveHimselfException(ErrorMessages.MODERATOR_CANNOT_REMOVE_HIMSELF);
        }

        if (!subreddit.getModerators().containsAll(Set.of(moderator, removedModerator))) {
            throw new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES);
        }

        subreddit.getModerators().remove(removedModerator);

        Subreddit savedSubreddit = subredditRepository.save(subreddit);

        return SubredditMapper.subredditToSubredditDto(savedSubreddit);
    }

    public void deleteSubreddit(Long id) {
        if (!subredditRepository.existsById(id)) {
            throw new SubredditNotFoundException(ErrorMessages.SUBREDDIT_NOT_FOUND);
        }
        subredditRepository.deleteById(id);
    }
}
