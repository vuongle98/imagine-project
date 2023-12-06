package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.LoginCommand;
import com.vuongle.imaginepg.application.commands.RegisterCommand;
import com.vuongle.imaginepg.application.dto.JwtResponse;
import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.domain.services.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final AuthService authService;

    public AuthController(
            AuthService authService
    ) {
        this.authService = authService;
    }

    @PostMapping("/token")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<JwtResponse> login(
            @RequestBody @Valid LoginCommand command
    ) {
        JwtResponse response = authService.login(command);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<UserDto> signUp(
            @RequestBody @Valid RegisterCommand registerCommand
    ) {
        UserDto response = authService.register(registerCommand);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    public ResponseEntity<UserDto> verify() {
        UserDto response = authService.verify();

        return ResponseEntity.ok(response);
    }
}
