package com.reddit.user;

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
