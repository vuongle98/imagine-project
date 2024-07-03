package com.vuongle.imaginepg.shared.utils;

import com.vuongle.imaginepg.domain.constants.Gender;
import com.vuongle.imaginepg.domain.constants.UserRole;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public CommandLineAppStartupRunner(
            UserRepository userRepository, PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        boolean isAdminExisted = userRepository.existsByUsername("admin");
        if (isAdminExisted) return;

        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .email("admin@email.com")
                .fullName("admin")
                .roles(Set.of(UserRole.ADMIN))
                .gender(Gender.MALE)
                .enabled(true)
                .address("")
                .birthday(Instant.now())
                .lastActive(Instant.now())
                .locked(false)
                .build();
        userRepository.save(admin);
    }
}
