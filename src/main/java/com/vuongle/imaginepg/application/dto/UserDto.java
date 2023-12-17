package com.vuongle.imaginepg.application.dto;

import com.vuongle.imaginepg.domain.constants.UserRole;
import com.vuongle.imaginepg.domain.entities.Friendship;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private UUID id;
    private String username;
    private String fullName;
    private String email;
    private Set<UserRole> roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.fullName = user.getFullName();
    }
}
