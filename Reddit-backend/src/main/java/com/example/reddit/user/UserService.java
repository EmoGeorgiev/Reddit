package com.example.reddit.user;

import com.example.reddit.exception.UserNotFoundException;
import com.example.reddit.util.ErrorMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public RedditUser getUserEntity(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }
    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        return UserMapper.userToUserDto(getUserEntity(id));
    }

    public UserDto updateUser(UserDto userDto) {
        throw new UnsupportedOperationException();
    }

    public void deleteUser(Long id) {
        throw new UnsupportedOperationException();
    }
}
