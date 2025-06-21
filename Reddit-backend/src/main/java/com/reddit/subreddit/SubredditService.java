package com.reddit.subreddit;

import com.reddit.exception.subreddit.MissingModeratorPrivilegesException;
import com.reddit.exception.subreddit.ModeratorCannotRemoveHimselfException;
import com.reddit.exception.subreddit.SubredditAlreadyExistsException;
import com.reddit.exception.subreddit.SubredditNotFoundException;
import com.reddit.exception.user.UserNotSubscribedException;
import com.reddit.user.RedditUser;
import com.reddit.user.UserService;
import com.reddit.util.ErrorMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<SubredditDto> getSubredditsByUserId(Long userId) {
        return subredditRepository
                .findByUsers_Id(userId)
                .stream()
                .map(SubredditMapper::subredditToSubredditDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getSubredditsWhereTitleContainsWord(String word) {
        return subredditRepository
                .findByTitleContainingIgnoreCase(word)
                .stream()
                .map(SubredditMapper::subredditToSubredditDto)
                .collect(Collectors.toList());
    }

    public SubredditDto addSubreddit(SubredditDto subredditDto, Long creatorId) {
        RedditUser user = userService.getUserEntity(creatorId);

        if (subredditRepository.findByTitle(subredditDto.title()).isPresent()) {
            throw new SubredditAlreadyExistsException(ErrorMessages.SUBREDDIT_ALREADY_EXISTS);
        }

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
