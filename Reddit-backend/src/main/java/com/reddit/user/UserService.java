package com.reddit.user;

import com.reddit.exception.subreddit.MissingModeratorPrivilegesException;
import com.reddit.exception.subreddit.SubredditNotFoundException;
import com.reddit.exception.user.*;
import com.reddit.subreddit.Subreddit;
import com.reddit.subreddit.SubredditRepository;
import com.reddit.user.dto.ModeratorUpdateDto;
import com.reddit.user.dto.UpdatePasswordDto;
import com.reddit.user.dto.UserDto;
import com.reddit.util.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final SubredditRepository subredditRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, SubredditRepository subredditRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.subredditRepository = subredditRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public RedditUser getUserEntityById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public RedditUser getUserEntityByUsername(String username) {
        return userRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USERNAME_DOES_NOT_EXIST));
    }

    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        return UserMapper.userToUserDto(getUserEntityById(id));
    }

    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        return userRepository
                .findByUsernameIgnoreCase(username)
                .map(UserMapper::userToUserDto)
                .orElseThrow(() -> new UsernameNotFoundException((ErrorMessages.USERNAME_DOES_NOT_EXIST)));
    }

    @Transactional(readOnly = true)
    public Page<UserDto> getUsers(Pageable pageable) {
        return userRepository
                .findAll(pageable)
                .map(UserMapper::userToUserDto);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> getUsersWhereUsernameContainsWord(String word, Pageable pageable) {
        return userRepository
                .findByUsernameContainingIgnoreCase(word, pageable)
                .map(UserMapper::userToUserDto);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUsersBySubredditTitle(String subredditTitle) {
        return userRepository
                .findBySubredditTitle(subredditTitle, Sort.by("username"))
                .stream()
                .map(UserMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDto> getModeratorsBySubredditTitle(String subredditTitle) {
        return userRepository
                .findModeratorsBySubredditTitle(subredditTitle, Sort.by("username"))
                .stream()
                .map(UserMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto addUser(String username, String password) {
        RedditUser user = new RedditUser();

        if (userRepository.findByUsernameIgnoreCase(username).isPresent()) {
            throw new UsernameAlreadyExistsException(ErrorMessages.USERNAME_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(password);
        user.setUsername(username);
        user.setPassword(encodedPassword);

        RedditUser savedUser = userRepository.save(user);

        return UserMapper.userToUserDto(savedUser);
    }

    public UserDto updateUsername(Long id, UserDto userDto) {
        RedditUser user = getUserEntityById(id);
        String username = userDto.username();

        if (userRepository.findByUsernameIgnoreCase(username).isPresent()) {
            throw new UsernameAlreadyExistsException(ErrorMessages.USERNAME_ALREADY_EXISTS);
        }

        user.setUsername(username);

        return UserMapper.userToUserDto(user);
    }

    public UserDto updatePassword(Long id, UpdatePasswordDto updatePasswordDto) {
        RedditUser user = getUserEntityById(id);
        String oldPassword = updatePasswordDto.oldPassword();
        String newPassword = updatePasswordDto.newPassword();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new PasswordsDoNotMatchException(ErrorMessages.PASSWORDS_DO_NOT_MATCH);
        }

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new NewPassWordCannotBeOldPasswordException(ErrorMessages.NEW_PASSWORD_CANNOT_BE_OLD_PASSWORD);
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);

        return UserMapper.userToUserDto(user);
    }

    public UserDto addSubredditModerator(String subredditTitle, ModeratorUpdateDto moderatorUpdateDto) {
        Long moderatorId = moderatorUpdateDto.moderatorId();
        String username = moderatorUpdateDto.updatedModeratorUsername();

        Subreddit subreddit = subredditRepository.findByTitleIgnoreCase(subredditTitle)
                                                .orElseThrow(() -> new SubredditNotFoundException(ErrorMessages.SUBREDDIT_NOT_FOUND));
        RedditUser moderator = getUserEntityById(moderatorId);
        RedditUser newModerator = getUserEntityByUsername(username);

        if (!subreddit.getModerators().contains(moderator)) {
            throw new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES);
        }

        if (!subreddit.getUsers().contains(newModerator)) {
            throw new UserNotSubscribedException(ErrorMessages.USER_NOT_SUBSCRIBED);
        }

        subreddit.getModerators().add(newModerator);

        newModerator.getModerated().add(subreddit);

        return UserMapper.userToUserDto(newModerator);
    }

    public UserDto removeSubredditModerator(String subredditTitle, ModeratorUpdateDto moderatorUpdateDto) {
        Long moderatorId = moderatorUpdateDto.moderatorId();
        String username = moderatorUpdateDto.updatedModeratorUsername();

        Subreddit subreddit = subredditRepository.findByTitleIgnoreCase(subredditTitle)
                                                .orElseThrow(() -> new SubredditNotFoundException(ErrorMessages.SUBREDDIT_NOT_FOUND));
        RedditUser moderator = getUserEntityById(moderatorId);
        RedditUser removedModerator = getUserEntityByUsername(username);

        if (!subreddit.getModerators().contains(moderator) || !subreddit.getModerators().contains(removedModerator)) {
            throw new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES);
        }

        subreddit.getModerators().remove(removedModerator);

        removedModerator.getModerated().remove(subreddit);

        return UserMapper.userToUserDto(removedModerator);
    }

    public void deleteUser(Long userId, String password) {
        RedditUser user = getUserEntityById(userId);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordsDoNotMatchException(ErrorMessages.PASSWORDS_DO_NOT_MATCH);
        }

        removeUserFromCollections(user);

        userRepository.deleteById(userId);
    }

    private void removeUserFromCollections(RedditUser user) {
        applyToCollection(user.getContents(), comment -> comment.setUser(null));

        removeUserFromCollection(user.getSubscribedTo(), subreddit -> subreddit.getUsers().remove(user));
        removeUserFromCollection(user.getModerated(), subreddit -> subreddit.getModerators().remove(user));
    }

    private <T> void applyToCollection(Collection<T> collection, Consumer<T> applier) {
        collection.forEach(applier);
    }

    private <T> void removeUserFromCollection(Collection<T> collection, Consumer<T> remover) {
        applyToCollection(collection, remover);
        collection.clear();
    }
}
