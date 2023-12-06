package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.LoginCommand;
import com.vuongle.imaginepg.application.commands.RegisterCommand;
import com.vuongle.imaginepg.application.dto.JwtResponse;
import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.application.exceptions.DataExistedException;
import com.vuongle.imaginepg.application.exceptions.UserNotFoundException;
import com.vuongle.imaginepg.domain.constants.UserRole;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.repositories.UserRepository;
import com.vuongle.imaginepg.domain.services.AuthService;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.JwtUtils;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public JwtResponse login(LoginCommand command) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();

        return JwtResponse
                .builder()
                .type("Bearer")
                .token(jwt)
                .user(new UserDto(userDetails))
                .build();
    }

    @Override
    public UserDto register(RegisterCommand command) {
        if (userRepository.existsByUsername(command.getUsername())) {
            throw new DataExistedException("Username has already existed");
        }

        if (userRepository.existsByEmail(command.getEmail())) {
            throw new DataExistedException("Email has already existed");
        }

        User user = new User(
                command.getUsername(),
                command.getFullName(),
                command.getEmail(),
                passwordEncoder.encode(command.getPassword()),
                List.of(UserRole.USER)
        );

        user = userRepository.save(user);

        return ObjectData.mapTo(user, UserDto.class);
    }

    @Override
    public UserDto verify() {
        User user = Context.getUser();

        if (Objects.isNull(user) || !user.isEnabled()) {
            throw new UserNotFoundException("User not found");
        }

        return ObjectData.mapTo(user, UserDto.class);
    }
}
