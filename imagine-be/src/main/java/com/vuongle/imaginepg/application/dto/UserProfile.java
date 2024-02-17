package com.vuongle.imaginepg.application.dto;

import com.vuongle.imaginepg.domain.constants.UserRole;
import com.vuongle.imaginepg.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile implements Serializable {

    private UUID id;
    private String username;
    private String fullName;
    private String email;
    private Set<UserRole> roles;
    private Set<UserDto> friends = new HashSet<>();

    private Set<FriendshipDto> friendships = new HashSet<>();

    public UserProfile(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.fullName = user.getFullName();
//        this.friends = user.getFriendships().stream().map(Friendship::getFriend).map(f-> ObjectData.mapTo(f, UserDto.class)).collect(Collectors.toSet());
//        this.friendships = user.getFriendships().stream().map(FriendshipDto::new).collect(Collectors.toSet());
    }

}
