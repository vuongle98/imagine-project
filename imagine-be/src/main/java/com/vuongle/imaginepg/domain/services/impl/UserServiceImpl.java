package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.RegisterCommand;
import com.vuongle.imaginepg.application.commands.UpdateFriendshipCommand;
import com.vuongle.imaginepg.application.commands.UpdateUserCommand;
import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.application.dto.UserProfile;
import com.vuongle.imaginepg.application.exceptions.DataExistedException;
import com.vuongle.imaginepg.application.exceptions.NoPermissionException;
import com.vuongle.imaginepg.application.exceptions.UserNotFoundException;
import com.vuongle.imaginepg.application.queries.UserFilter;
import com.vuongle.imaginepg.domain.constants.FriendStatus;
import com.vuongle.imaginepg.domain.constants.UserRole;
import com.vuongle.imaginepg.domain.entities.Friendship;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.mapper.UserMapper;
import com.vuongle.imaginepg.domain.repositories.UserRepository;
import com.vuongle.imaginepg.domain.services.UserService;
import com.vuongle.imaginepg.infrastructure.specification.UserSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return getById(id, UserDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {

        User user = userRepository.getById(id);

        return ObjectData.mapTo(user, classType);
    }

    @Override
    public UserDto create(UpdateUserCommand command) {
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

        return UserMapper.mapToDto(user);
    }

    @Override
    public UserDto update(UUID id, UpdateUserCommand command) {

        User existedUser = getById(id, User.class);

        if (Context.getUser() == null) throw new UserNotFoundException("Current user not found");

        if (!Context.getUser().getId().equals(id) && !Context.hasModifyPermission()) throw new NoPermissionException("Cannot update other profile");

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

        if (Objects.nonNull(command.getFriendshipData())) {
            for (var friendShip: command.getFriendshipData()) {
                switch (friendShip.getStatus()) {
                    case REQUESTED -> addFriend(existedUser, friendShip.getFriendId());
                    case ACCEPTED -> acceptFriend(existedUser, friendShip.getFriendId());
                    case REJECTED -> declineFriend(existedUser, friendShip.getFriendId());
                    case REMOVE -> removeFriend(existedUser, friendShip.getFriendId());
                }
            }
        }


        existedUser = userRepository.save(existedUser);
        return UserMapper.mapToDto(existedUser);
    }

    @Override
    public void delete(UUID id, boolean force) {

        // check permission
        if (!Context.hasModifyPermission()) {
            throw new NoPermissionException("No permission");
        }

        User user = getById(id, User.class);

        if (force) {
            userRepository.deleteById(id);
        }

    }

    @Override
    public Page<UserDto> getAll(UserFilter filter, Pageable pageable) {
        Specification<User> userSpecification = UserSpecifications.withFilter(filter);
        Page<User> userPage = userRepository.findAll(userSpecification, pageable);
        return userPage.map(UserMapper::mapToDto);
    }

    @Override
    public List<UserDto> getAll(UserFilter filter) {
        Specification<User> userSpecification = UserSpecifications.withFilter(filter);
        List<User> users = userRepository.findAll(userSpecification);

        return users.stream().map(UserMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return UserMapper.mapToDto(user.get());
        }

        throw new UserNotFoundException("Not found user " + username);

    }

    @Override
    public UserProfile getProfileByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return UserMapper.mapToProfile(user.get());
        }

        throw new UserNotFoundException("Not found user " + username);

    }

    @Override
    public UserDto addFriend(UUID friendId) {
        return changeFriendShipStatus(friendId, FriendStatus.REQUESTED);
    }

    @Override
    public UserDto acceptFriend(UUID friendId) {
        return changeFriendShipStatus(friendId, FriendStatus.ACCEPTED);
    }

    @Override
    public UserDto declineFriend(UUID friendId) {
        return changeFriendShipStatus(friendId, FriendStatus.REJECTED);
    }

    @Override
    public UserDto removeFriend(UUID friendId) {
        return changeFriendShipStatus(friendId, FriendStatus.REMOVE);
    }

    @Override
    public UserDto setUserOnline(UUID userId, boolean online) {
        User user = getById(userId, User.class);

        if (Objects.isNull(user)) {
            throw new UserNotFoundException("User not found");
        }

        user.setOnline(online);

        if (!online) {
            user.setLastActive(Instant.now());
        }

        user = userRepository.save(user);
        return new UserDto(user);
    }

    @Override
    public boolean isExisted(UUID userId) {
        return getById(userId) != null;
    }

    private void addFriend(User user, UUID friendId) {

        if (user.getId().equals(friendId)) {
            throw new RuntimeException("Cannot add yourself");
        }

        User friend = getById(friendId, User.class);
        if (Objects.isNull(friend)) {
            throw new UserNotFoundException("Friend not found");
        }

        user.addFriend(friend);

        friend.pendingFriend(user);
        userRepository.save(friend);
    }

    private void acceptFriend(User user, UUID friendId) {

        if (user.getId().equals(friendId)) {
            throw new RuntimeException("Cannot accept yourself");
        }

        // no need to check if friendId exists because it is checked in addFriend
        // current user accept friend request
        User friend = getById(friendId, User.class);
        if (Objects.isNull(friend)) {
            throw new UserNotFoundException("Friend not found");
        }

        user.acceptFriend(friend);

        friend.acceptFriend(user);
        userRepository.save(friend);
    }

    private void declineFriend(User user, UUID friendId) {

        if (user.getId().equals(friendId)) {
            throw new RuntimeException("Cannot decline yourself");
        }

        User friend = getById(friendId, User.class);
        if (Objects.isNull(friend)) {
            throw new UserNotFoundException("Friend not found");
        }

        user.declineFriend(friend);

        friend.declineFriend(user);
        userRepository.save(friend);
    }

    private void removeFriend(User user, UUID friendId) {

        if (user.getId().equals(friendId)) {
            throw new RuntimeException("Cannot remove yourself");
        }

        User friend = getById(friendId, User.class);
        if (Objects.isNull(friend)) {
            throw new UserNotFoundException("Friend not found");
        }

        user.removeFriend(friend);
        friend.removeFriend(user);
        userRepository.save(friend);
    }

    private UserDto changeFriendShipStatus(UUID friendId, FriendStatus status) {

        User currentUser = Context.getUser();

        if (Objects.isNull(currentUser)) {
            throw new UserNotFoundException("Not found user");
        }

        if (currentUser.getId().equals(friendId)) {
            throw new RuntimeException("Cannot perform on yourself");
        }

        UpdateFriendshipCommand friendShip = new UpdateFriendshipCommand();
        friendShip.setFriendId(friendId);
        friendShip.setStatus(status);

        UpdateUserCommand command = new UpdateUserCommand();
        command.setFriendshipData(List.of(friendShip));

        return update(currentUser.getId(), command);
    }
}
