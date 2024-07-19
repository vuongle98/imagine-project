package com.vuongle.imaginepg.domain.mappers;

import com.vuongle.imaginepg.application.dto.FriendshipDto;
import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.domain.entities.Friendship;

public class FriendshipMapper {

    public static FriendshipDto mapToDto(Friendship friendship) {
        FriendshipDto friendshipDto = new FriendshipDto();
        friendshipDto.setId(friendship.getId());
        friendshipDto.setStatus(friendship.getStatus());
        friendshipDto.setFriend(new UserDto(friendship.getFriend()));
        friendshipDto.setUser(new UserDto(friendship.getUser()));
        // You can map other fields as needed
        return friendshipDto;
    }
}
