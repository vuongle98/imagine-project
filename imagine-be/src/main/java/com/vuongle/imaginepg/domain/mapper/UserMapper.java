package com.vuongle.imaginepg.domain.mapper;

import com.vuongle.imaginepg.application.dto.FriendshipDto;
import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.application.dto.UserProfile;
import com.vuongle.imaginepg.domain.entities.Friendship;
import com.vuongle.imaginepg.domain.entities.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserProfile mapToProfile(User user) {
        UserProfile profile = new UserProfile();
        profile.setId(user.getId());
        profile.setUsername(user.getUsername());
        profile.setFriendships(mapFriendshipsToDto(user.getFriendships()));
        profile.setFullName(user.getFullName());
        profile.setEmail(user.getEmail());
        profile.setRoles(user.getRoles());
        profile.setFriends(profile.getFriendships().stream().map(FriendshipDto::getFriend).collect(Collectors.toSet()));
        return profile;
    }

    public static UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    private static Set<FriendshipDto> mapFriendshipsToDto(Set<Friendship> friendships) {
        return friendships.stream()
                .map(FriendshipMapper::mapToDto)
                .collect(Collectors.toSet());
    }

}
