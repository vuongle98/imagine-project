package com.vuongle.imaginepg.application.dto;

import com.vuongle.imaginepg.domain.constants.FriendStatus;
import com.vuongle.imaginepg.domain.entities.Friendship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipDto implements Serializable {

    private UUID id;

    private UserDto user;

    private UserDto friend;

    private FriendStatus status;

    private Instant updateFrom = Instant.now();

    private Instant friendFrom = Instant.now();

    public FriendshipDto(Friendship friendship) {
        this.id = friendship.getId();
        this.user = new UserDto(friendship.getUser());
        this.friend = new UserDto(friendship.getFriend());
        this.status = friendship.getStatus();
        this.updateFrom = friendship.getUpdateFrom();
        this.friendFrom = friendship.getFriendFrom();
    }

}
