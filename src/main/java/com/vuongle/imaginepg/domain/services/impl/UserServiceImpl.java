package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.RegisterCommand;
import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.application.exceptions.DataExistedException;
import com.vuongle.imaginepg.application.queries.UserFilter;
import com.vuongle.imaginepg.domain.constants.UserRole;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.repositories.UserRepository;
import com.vuongle.imaginepg.domain.services.UserService;
import com.vuongle.imaginepg.infrastructure.specification.UserSpecifications;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto getById(UUID id) {
        User user = userRepository.getById(id);
        return ObjectData.mapTo(user, UserDto.class);
    }

    @Override
    public UserDto create(RegisterCommand command) {
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
    public UserDto update(UUID id, RegisterCommand command) {

        User existedUser = userRepository.getById(id);

        if (Objects.nonNull(command.getEmail())) {
            // check email existed
            if (userRepository.existsByUsername(command.getUsername())) {
                throw new DataExistedException("Username has already existed");
            }

            existedUser.setEmail(command.getEmail());
        }

        if (Objects.nonNull(command.getFullName())) {
            existedUser.setFullName(command.getFullName());
        }

        if (Objects.nonNull(command.getPassword())) {
            existedUser.setPassword(passwordEncoder.encode(command.getPassword()));
        }

        existedUser = userRepository.save(existedUser);
        return ObjectData.mapTo(existedUser, UserDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {

        if (force) {
            userRepository.deleteById(id);
        }

    }

    @Override
    public Page<UserDto> getAll(UserFilter filter, Pageable pageable) {
        Specification<User> userSpecification = UserSpecifications.withFilter(filter);
        Page<User> userPage = userRepository.findAll(userSpecification, pageable);
        return userPage.map(u -> ObjectData.mapTo(u, UserDto.class));
    }

    @Override
    public List<UserDto> getAll(UserFilter filter) {
        Specification<User> userSpecification = UserSpecifications.withFilter(filter);
        return ObjectData.mapListTo(userRepository.findAll(userSpecification), UserDto.class);
    }
}
