package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.services.UserService;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<UserDto> profile() {
        User user = Context.getUser();

        return ResponseEntity.ok(ObjectData.mapTo(user, UserDto.class));
    }
}
