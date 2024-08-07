package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.UpdateUserCommand;
import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.application.dto.UserProfile;
import com.vuongle.imaginepg.application.queries.UserFilter;

import java.util.UUID;

public interface UserService extends BaseService<UserDto, UpdateUserCommand>, BaseQueryService<UserDto, UserFilter> {

    UserDto getByUsername(String username);

    UserProfile getProfileByUsername(String username);

    UserDto addFriend(UUID friendId);

    UserDto acceptFriend(UUID friendId);

    UserDto declineFriend(UUID friendId);

    UserDto removeFriend(UUID friendId);

    UserDto setUserOnline(UUID userId, boolean online);

    boolean isExisted(UUID userId);
}
