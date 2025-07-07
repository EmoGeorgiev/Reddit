package com.reddit.subreddit;

import com.reddit.exception.subreddit.MissingModeratorPrivilegesException;
import com.reddit.exception.subreddit.SubredditAlreadyExistsException;
import com.reddit.exception.subreddit.SubredditNotFoundException;
import com.reddit.exception.user.UserNotSubscribedException;
import com.reddit.subreddit.dto.ModeratorUpdateDto;
import com.reddit.subreddit.dto.SubredditDto;
import com.reddit.subreddit.dto.SubredditUpdateTitleDto;
import com.reddit.user.RedditUser;
import com.reddit.user.UserService;
import com.reddit.util.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
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
    public SubredditDto getSubredditByTitle(String title) {
        return subredditRepository
                .findByTitleIgnoreCase(title)
                .map(SubredditMapper::subredditToSubredditDto)
                .orElseThrow(() -> new SubredditNotFoundException(ErrorMessages.SUBREDDIT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubredditById(Long id) {
        return SubredditMapper.subredditToSubredditDto(getSubredditEntity(id));
    }

    @Transactional(readOnly = true)
    public Page<SubredditDto> getSubredditsWhereTitleContainsWord(String word, Pageable pageable) {
        return subredditRepository
                .findByTitleContainingIgnoreCase(word, pageable)
                .map(SubredditMapper::subredditToSubredditDto);
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getSubredditsByUserId(Long userId) {
        return subredditRepository
                .findByUsers_Id(userId, Sort.by("title"))
                .stream()
                .map(SubredditMapper::subredditToSubredditDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Set<SubredditDto> getSubredditsByModeratorId(Long moderatorId) {
        return subredditRepository
                .findByModerators_Id(moderatorId)
                .stream()
                .map(SubredditMapper::subredditToSubredditDto)
                .collect(Collectors.toSet());
    }

    public SubredditDto addSubreddit(SubredditDto subredditDto, Long creatorId) {
        RedditUser user = userService.getUserEntity(creatorId);

        if (subredditRepository.findByTitleIgnoreCase(subredditDto.title()).isPresent()) {
            throw new SubredditAlreadyExistsException(ErrorMessages.SUBREDDIT_ALREADY_EXISTS);
        }

        Subreddit subreddit = new Subreddit();
        subreddit.setTitle(subredditDto.title());
        subreddit.getUsers().add(user);
        subreddit.getModerators().add(user);

        user.getSubscribedTo().add(subreddit);
        user.getModerated().add(subreddit);

        Subreddit savedSubreddit = subredditRepository.save(subreddit);

        return SubredditMapper.subredditToSubredditDto(savedSubreddit);
    }

    public SubredditDto updateSubredditTitle(Long subredditId, SubredditUpdateTitleDto subredditUpdateTitleDto) {
        String newTitle = subredditUpdateTitleDto.title();
        Long moderatorId = subredditUpdateTitleDto.moderatorId();

        Subreddit subreddit = getSubredditEntity(subredditId);
        RedditUser user = userService.getUserEntity(moderatorId);

        if (!subreddit.getModerators().contains(user)) {
            throw new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES);
        }

        subreddit.setTitle(newTitle);

        return SubredditMapper.subredditToSubredditDto(subreddit);
    }

    public SubredditDto addSubredditModerator(Long subredditId, ModeratorUpdateDto moderatorUpdateDto) {
        Long moderatorId = moderatorUpdateDto.moderatorId();
        Long newModeratorId = moderatorUpdateDto.updatedModeratorId();

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

        newModerator.getModerated().add(subreddit);

        return SubredditMapper.subredditToSubredditDto(subreddit);
    }

    public SubredditDto removeSubredditModerator(Long subredditId, ModeratorUpdateDto moderatorUpdateDto) {
        Long moderatorId = moderatorUpdateDto.moderatorId();
        Long removedModeratorId = moderatorUpdateDto.updatedModeratorId();

        Subreddit subreddit = getSubredditEntity(subredditId);
        RedditUser moderator = userService.getUserEntity(moderatorId);
        RedditUser removedModerator = userService.getUserEntity(removedModeratorId);

        if (!subreddit.getModerators().contains(moderator) || !subreddit.getModerators().contains(removedModerator)) {
            throw new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES);
        }

        subreddit.getModerators().remove(removedModerator);

        moderator.getModerated().remove(subreddit);

        return SubredditMapper.subredditToSubredditDto(subreddit);
    }

    public void deleteSubreddit(Long subredditId, Long moderatorId) {
        Subreddit subreddit = getSubredditEntity(subredditId);
        RedditUser moderator = userService.getUserEntity(moderatorId);

        if (!subreddit.getModerators().contains(moderator)) {
            throw new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES);
        }

        removeSubredditFromCollections(subreddit);

        subredditRepository.deleteById(subredditId);
    }

    private void removeSubredditFromCollections(Subreddit subreddit) {
        removeSubredditFromCollection(subreddit.getUsers(), user -> user.getSubscribedTo().remove(subreddit));
        removeSubredditFromCollection(subreddit.getModerators(), moderator -> moderator.getModerated().remove(subreddit));
    }

    private <T> void removeSubredditFromCollection(Collection<T> collection, Consumer<T> remover) {
        collection.forEach(remover);
        collection.clear();
    }
}
