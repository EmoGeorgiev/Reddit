package com.reddit.user;

import com.reddit.user.dto.UserDto;

public class UserMapper {
    public static UserDto userToUserDto(RedditUser user) {
        if (user == null) {
            return null;
        }

        return new UserDto(
                user.getId(),
                user.getUsername());
    }
}
