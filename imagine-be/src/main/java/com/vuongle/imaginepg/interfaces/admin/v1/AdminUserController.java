package com.vuongle.imaginepg.interfaces.admin.v1;

import com.vuongle.imaginepg.application.commands.UpdateUserCommand;
import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.application.queries.UserFilter;
import com.vuongle.imaginepg.domain.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<Page<UserDto>> searchUser(
            UserFilter userFilter,
            Pageable pageable
    ) {
        Page<UserDto> userPage = userService.getPageable(userFilter, pageable);

        return ResponseEntity.ok(userPage);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<UserDto> create(
            @RequestBody UpdateUserCommand command
    ) {
        UserDto user = userService.create(command);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<UserDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        UserDto user = userService.getById(id);

        return ResponseEntity.ok(user);
    }
}
