package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.application.dto.UserProfile;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.mapper.UserMapper;
import com.vuongle.imaginepg.domain.services.UserService;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<UserProfile> profile(
            @RequestParam(value = "username") String username
    ) {
        User user = Context.getUser();

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        UserProfile foundProfile = UserMapper.mapToProfile(user);

        if (Objects.nonNull(username) && !foundProfile.getUsername().equals(username)) {
            foundProfile = userService.getProfileByUsername(username);
        }

        if (Objects.isNull(foundProfile)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundProfile);
    }

    @PutMapping("/add-friend")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<UserDto> addFriend(
            @RequestParam("friend-id") UUID friendId
    ) {
        UserDto userProfile = userService.addFriend(friendId);
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/accept-friend")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<UserDto> acceptFriend(
            @RequestParam("friend-id") UUID friendId
    ) {
        UserDto userProfile = userService.acceptFriend(friendId);
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/decline-friend")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<UserDto> declineFriend(
            @RequestParam("friend-id") UUID friendId
    ) {
        UserDto userProfile = userService.declineFriend(friendId);
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/remove-friend")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<UserDto> removeFriend(
            @RequestParam("friend-id") UUID friendId
    ) {
        UserDto userProfile = userService.removeFriend(friendId);
        return ResponseEntity.ok(userProfile);
    }
}
